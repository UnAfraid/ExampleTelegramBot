# Example Telegram Bot [![Deploy](https://www.herokucdn.com/deploy/button.svg)](https://heroku.com/deploy?template=https://github.com/UnAfraid/ExampleTelegramBot)
[![Build Status](https://img.shields.io/endpoint.svg?url=https%3A%2F%2Factions-badge.atrox.dev%2FUnAfraid%2FExampleTelegramBot%2Fbadge&style=flat)](https://actions-badge.atrox.dev/UnAfraid/ExampleTelegramBot/goto) ![License: MIT](https://img.shields.io/badge/License-MIT-blue.svg) [![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=UnAfraid_ExampleTelegramBot&metric=alert_status)](https://sonarcloud.io/dashboard?id=UnAfraid_ExampleTelegramBot) [![Reliability Rating](https://sonarcloud.io/api/project_badges/measure?project=UnAfraid_ExampleTelegramBot&metric=reliability_rating)](https://sonarcloud.io/dashboard?id=UnAfraid_ExampleTelegramBot) [![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=UnAfraid_ExampleTelegramBot&metric=security_rating)](https://sonarcloud.io/dashboard?id=UnAfraid_ExampleTelegramBot) [![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=UnAfraid_ExampleTelegramBot&metric=sqale_rating)](https://sonarcloud.io/dashboard?id=UnAfraid_ExampleTelegramBot) [![Bugs](https://sonarcloud.io/api/project_badges/measure?project=UnAfraid_ExampleTelegramBot&metric=bugs)](https://sonarcloud.io/dashboard?id=UnAfraid_ExampleTelegramBot)

This repository contains an example of telegram bot written in Java (11 and above)

Current version supports the following commands:
* /help - Displays help about all or specified command
* /whoami - Displays information about the person who wrote the command: User Id, Name and chat type
* /start - The default bot command, shows greeting message
* /menu - Shows example of Inline Menu with buttons

### Configuration
Configuration is supplied through Environment Variables:
```env
EXAMPLE_TG_BOT_USERNAME=ExampleTelegramBot # The bot name from BotFather
EXAMPLE_TG_BOT_TOKEN=123455:abcd.. # The token from BotFather
EXAMPLE_TG_BOT_ADMIN_IDS=123456 # User ids separated by comma, allowing them to access /menu (You can obtain that id by typing /whoami)
```
Click here to talk to [BotFather](https://t.me/BotFather)

There are multiple ways to start this example, here are two:

### Docker with docker-compose
You can run this example with docker.

First create new file called .env with the content mentioned [Environment Variables](#configuration) above.
1. Create and start the container
    ```shell script
    docker-compose up -d
    ```
2. Check the logs for errors
    ```shell script
    docker-compose logs
    ```
3. If bot is working correctly you should see the following logs
    ```shell script
    Attaching to example_telegram_bot
    example_telegram_bot | [20:30:06] [INFO] Main: Initializing ** Your bot name here** ...
    example_telegram_bot | [20:30:06] [INFO] Main: Authorized admin ids: [**Your ids here**]
    example_telegram_bot | [20:30:07] [INFO] Main: Initialization done
    ```
 4. If you want to stop the bot
    ```shell script
    docker-compose down
    ```

### Source
Alternatively you can run the example from source
1. Clone the repository
     ```shell script
    git clone https://github.com/UnAfraid/ExampleTelegramBot.git
    ```
2. Build the source
    ```shell script
   # For Windows
    gradlew.bat installDist

   # For Linux/Mac
   ./gradlew installDist
    ```
3. Run the bot
    ```shell script
    # Set Environment Variables
   
    # For Windows
    set EXAMPLE_TG_BOT_USERNAME=ExampleTelegramBot # The bot name from BotFather
    set EXAMPLE_TG_BOT_TOKEN=123455:abcd.. # The token from BotFather
    set EXAMPLE_TG_BOT_ADMIN_IDS=123456 # User ids separated by comma, allowing them to access /menu (You can obtain that id by typing /whoami)

    # For Linux/Mac
    export EXAMPLE_TG_BOT_USERNAME=ExampleTelegramBot # The bot name from BotFather
    export EXAMPLE_TG_BOT_TOKEN=123455:abcd.. # The token from BotFather
    export EXAMPLE_TG_BOT_ADMIN_IDS=123456 # User ids separated by comma, allowing them to access /menu (You can obtain that id by typing /whoami)
    
    # Navigate to the bot build directory
    cd build/install/ExampleTelegramBot

    # Run the bot
    java --add-opens java.base/java.lang=ALL-UNNAMED -jar ExampleTelegramBot.jar
    ```
 4. If bot is working correctly you should see the following logs
    ```shell script
    [20:30:06] [INFO] Main: Initializing ** Your bot name here** ...
    [20:30:06] [INFO] Main: Authorized admin ids: [**Your ids here**]
    [20:30:07] [INFO] Main: Initialization done
    ```
     
