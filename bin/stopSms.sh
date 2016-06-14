#!/bin/bash

ps -ef | grep SMSMain | grep -v "grep" | awk '{print $2}' | xargs kill -9
