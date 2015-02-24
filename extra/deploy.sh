#! /bin/sh
#

# source function library	
. /etc/rc.d/init.d/functions


APP_PATH=$1      #/var/www/sites/app
DESC=$2          #"Analytics Api Dev Server"
PID_FILE=$3      #/var/run/analytics-api-dev.pid
PORT=$4          #
CONFIG_FILE=$5   #

NAME=analytics-api
COMMAND=${APP_PATH}/target/universal/stage/bin/${NAME}
OPT="-DapplyEvolutions.default=true -DapplyDownEvolutions.default=true -Dpidfile.path=${PID_FILE} -Dhttp.port=${PORT} -Dconfig.file=${CONFIG_FILE}"

start()
{
    echo -n $"Starting $DESC: "
    cd $APP_PATH
    TMP_LOG=`su - some_user -c mktemp`
    daemon --pidfile ${PID_FILE} --user some_user "${COMMAND} ${OPT} > /dev/null 2> ${TMP_LOG} &"
    RETVAL=$?
    echo
    return $RETVAL
}

stop()
{
    echo -n $"Stopping $DESC: "
    killproc -p $PID_FILE $NAME
    RETVAL=$?
    echo
    return $RETVAL
}

case "$6" in
    start)
        start
        ;;

    stop)
        stop
        ;;

    restart)
        stop
        start
        ;;

    *)
        echo $"Usage: $0 {start|stop|restart}"
        RETVAL=2
esac

exit $RETVAL