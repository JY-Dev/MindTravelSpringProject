APPLICATION=mind-travel
echo $APPLICATION
IS_RUNNING=$(sudo docker compose ls | grep $APPLICATION | grep running | sed 's/.*/true/')

if [ "$IS_RUNNING" = "true" ] 
then
    echo $APPLICATION 실행중
    IS_BLUE_RUNNING=$(sudo docker-compose -p $APPLICATION ps | grep blue | grep Up | sed 's/.*/true/')
    echo $CUR_APPLICATION
    if [ "$IS_BLUE_RUNNING" = "true" ]
    then
        CUR_APPLICATION="blue"
        DEPLOY_APPLICATION="green"
        cp green_nginx.conf nginx.conf
    else
        CUR_APPLICATION="green"
        DEPLOY_APPLICATION="blue"
        cp blue_nginx.conf nginx.conf
    fi

    echo 사용하지 않는 이미지 정리
    sudo docker rmi $(sudo docker images -f "dangling=true" -q)
    
    echo 배포시작 : $DEPLOY_APPLICATION
    sudo docker-compose -p mind-travel up --build -d --no-deps $DEPLOY_APPLICATION
    
    sleep 5
    
    #배포 어플리케이션 Health Check
    for i in {1..10}; do
        DEPLOY_STATUS=$(sudo docker-compose -p $APPLICATION ps $DEPLOY_APPLICATION | grep "Up" | sed 's/.*/true/')
        if [ "$DEPLOY_STATUS" != "true" ]
        then
            if [i = 10]
            then   
                echo 배포실패
            else
                echo 배포중.....
                echo time : $i
            fi
            sleep 1
        else
            echo 배포성공
			      sudo docker-compose -p $APPLICATION exec nginx nginx -s reload
			      sudo docker-compose -p $APPLICATION stop $CUR_APPLICATION
            break
        fi
    done
    
else
    echo $APPLICATION 실행중이지 않음
    sudo docker-compose build --no-cache green blue
    sudo docker-compose -p $APPLICATION up -d
    cp blue_nginx.conf nginx.conf
    sudo docker-compose -p $APPLICATION exec nginx nginx -s reload
    sudo docker-compose -p $APPLICATION stop green
    echo $APPLICATION 실행
fi
