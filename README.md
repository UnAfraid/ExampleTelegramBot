# Example Telegram Bot
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
    example_telegram_bot | WARNING: An illegal reflective access operation has occurred ...
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
    java -jar ExampleTelegramBot.jar
    ```
 4. If bot is working correctly you should see the following logs
    ```shell script
    [20:30:06] [INFO] Main: Initializing ** Your bot name here** ...
    [20:30:06] [INFO] Main: Authorized admin ids: [**Your ids here**]
    WARNING: An illegal reflective access operation has occurred ...
    [20:30:07] [INFO] Main: Initialization done
    ```
     