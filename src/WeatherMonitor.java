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
    private final static int DEFAULT_CAPACITY = 5;
    IntStack weather_stack;
    IntStack reverse_stack;

    public WeatherMonitor() {
        this.weather_stack = new IntStack(DEFAULT_CAPACITY);
        this.reverse_stack = new IntStack(DEFAULT_CAPACITY);
    }
    
    public int numDays(int temp) {

        if(weather_stack.size() == 0){
            weather_stack.push(temp);
            return 0;
        }
        int recorded_weather_size = weather_stack.size();

        for(int i = 0; i < recorded_weather_size;i++){
           if(temp > weather_stack.peek()){
               reverse_stack.push(weather_stack.pop());
           }
           else if(weather_stack.peek() >= temp){
               break;
           }
        }

        if(reverse_stack.isEmpty()){
           weather_stack.push(temp);
           return 0;
        }

        int greater_count = reverse_stack.size();

        weather_stack.multiPush(reverse_stack.multiPop(reverse_stack.size()));
        weather_stack.push(temp);

        return greater_count;
    }
    
}

