APPLICATION=mind-travel
echo $APPLICATION
IS_RUNNING=$(docker compose ls | grep $APPLICATION | grep running | sed 's/.*/true/')  

if [ "$IS_RUNNING" = "true" ] 
then
    echo $APPLICATION 실행중
    CUR_APPLICATION=$(docker-compose -p $APPLICATION exec nginx sh -c '. /tmp/env;echo "$CUR_APPLICATION"')
    echo $CUR_APPLICATION
    if [ "$CUR_APPLICATION" = "blue" ]
    then
        DEPLOY_APPLICATION="green"
        cp green_nginx.conf nginx.conf
    else
        DEPLOY_APPLICATION="blue"
        cp blue_nginx.conf nginx.conf
    fi
    
    echo 배포시작 : $DEPLOY_APPLICATION
    docker-compose -p mind-travel up --build -d --no-deps $DEPLOY_APPLICATION
    
    #배포 어플리케이션 Health Check
    for i in {1..10}; do
        DEPLOY_STATUS="$(docker-compose -p $APPLICATION ps $DEPLOY_APPLICATION | grep "Up" | sed 's/.*/true/')"
        if [ "$DEPLOY_STATUS" != "true" ]
        then
            if [i = 10]
            then   
                echo 배포실패
            else
                docker-compose -p mind-travel up -d --no-deps $DEPLOY_APPLICATION    
                echo 배포중.....
                echo time : $i
            fi
            sleep 1
        else
            echo 배포성공
            docker-compose -p $APPLICATION exec nginx sh -c "export CUR_APPLICATION='$DEPLOY_APPLICATION'; env > /tmp/env"
            echo $(docker-compose -p $APPLICATION exec nginx sh -c '. /tmp/env;echo $CUR_APPLICATION')
            break
        fi
    done
    
else
    echo $APPLICATION 실행중이지 않음
    docker-compose build --no-cache green blue
    docker-compose -p $APPLICATION up -d
    echo $APPLICATION 실행
fi
