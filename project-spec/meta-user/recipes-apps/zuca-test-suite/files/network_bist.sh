# !/bin/sh

ARGS_WIFI=3
ARGS_ETH=1

if [ $# -ne "$ARGS_ETH" ] && [ $# -ne "$ARGS_WIFI" ]; then
  echo "Wrong number of arguments!"
  exit 1
fi

if [ $1 != "eth0" ] && [ $1 != "wlan0" ]; then
  echo "Wrong network interfaces!"
  exit 1
fi

if [ $1 = "eth0" ] && [ $# -ne "$ARGS_ETH" ]; then
  echo "Incorrent arguments for eth0 interface testing!"
  exit 1
fi

if [ $1 = "wlan0" ] && [ $# -ne "$ARGS_WIFI" ]; then
  echo "Incorrent arguments for wlan0 interface testing!"
  exit 1
fi

# Set the testing interface down
ip link set "$1" down
echo "We set $1 down."
if [ "$?" != 0 ]; then
  echo "<ip link set $1 down> command failed!"
  exit 2
fi

# Set the testing interface up
ip link set "$1" up
echo "We set $1 up."
if [ "$?" != 0 ]; then
  echo "<ip link set $1 up> command failed!"
  exit 2
fi

if [ $1 = "wlan0" ]; then
  ./wifi_bist.sh "$2" "$3"
  if [ "$?" != 0 ]; then
    echo "WPA configuration script failed!"
    exit 1
  fi
fi

dhclient -1 -v $1
if [ "$?" != 0 ]; then
  echo "<dhclient $1> command failed!"
  exit 6
else
  echo "Host configuration DONE"
fi

ROUTER_ADDRESS=$(ip route | grep $1 | grep -m1 default | awk '{ print $3 }')
echo "$ROUTER_ADDRESS"
if [ -z "$ROUTER_ADDRESS" ]; then
  echo "No IP received"
  exit 7
fi

# Ping the router
ping -c1 -I $1 $ROUTER_ADDRESS > /dev/null
if [ "$?" = 0 ]; then
  exit 0
else
  echo "<ping -c1 $ROUTER_ADDRESS > /dev/null> command failed!"
  exit 8
fi
