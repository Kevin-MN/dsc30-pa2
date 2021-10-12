/*
    Name: Kevin Morales-Nguyen
    PID:  A17186624
 */

/**
 * This is a simple class that utilizes the IntStack class to record weather data,
 * and compute related information showcasing the ADT stack's capabilities.
 *
 * @author Kevin Morales-Nguyen
 * @since  10/11/21
 */

public class WeatherMonitor {
    IntStack weather_stack;
    IntStack temp_stack;

    public WeatherMonitor() {
        this.weather_stack = new IntStack(5);
        this.temp_stack = new IntStack(5);
    }
    
    public int numDays(int temp) {

        if(weather_stack.size() == 0){
            weather_stack.push(temp);
            return 0;
        }
        int weather_size = weather_stack.size();

        for(int i = 0; i < weather_size;i++){
           if(temp > weather_stack.peek()){
               temp_stack.push(weather_stack.pop());
           }
           else if(weather_stack.peek() >= temp){
               break;
           }
        }

        if(temp_stack.isEmpty()){
           weather_stack.push(temp);
           return 0;
        }

        int temp_nums = temp_stack.size();

        weather_stack.multiPush(temp_stack.multiPop(temp_stack.size()));
        weather_stack.push(temp);

        return temp_nums;
    }
    
}