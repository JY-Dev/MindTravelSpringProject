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
        sudo cp green_nginx.conf nginx.conf
    else
        CUR_APPLICATION="green"
        DEPLOY_APPLICATION="blue"
        sudo cp blue_nginx.conf nginx.conf
    fi

    echo 사용하지 않는 이미지 정리
    sudo docker rmi $(sudo docker images -f "dangling=true" -q)

    echo 배포시작 : $DEPLOY_APPLICATION
    sudo docker-compose -p mind-travel up --build -d --no-deps $DEPLOY_APPLICATION

    sleep 30

    DEPLOY_STATUS=$(sudo docker-compose -p $APPLICATION ps $DEPLOY_APPLICATION | grep "Up" | sed 's/.*/true/')
    echo $(sudo docker-compose -p $APPLICATION ps $DEPLOY_APPLICATION)
    if [ "$DEPLOY_STATUS" != "true" ]
    then
        echo 배포실패
    else
        echo 배포 성공
        sudo docker-compose -p $APPLICATION exec nginx nginx -s reload
        sudo docker-compose -p $APPLICATION stop $CUR_APPLICATION
        break
    fi
else
    echo $APPLICATION 실행중이지 않음
    sudo docker-compose build --no-cache green blue
    sudo docker-compose -p $APPLICATION up -d
    sudo cp blue_nginx.conf nginx.conf
    sleep 30
    sudo docker-compose -p $APPLICATION exec nginx nginx -s reload
    sudo docker-compose -p $APPLICATION stop green
    echo $APPLICATION 실행
fi