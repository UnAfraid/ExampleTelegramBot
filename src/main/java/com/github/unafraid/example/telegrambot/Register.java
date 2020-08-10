import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class Register {
    public static void main(String[] args){
        ApiContextInitializer.init();
        TelegramBotsApi bot = new TelegramBotsApi();
        try{
            bot.registerBot(new CloneMainCampbot());
        }catch(TelegramApiException e){
            e.printStackTrace();
        }
    }
}
