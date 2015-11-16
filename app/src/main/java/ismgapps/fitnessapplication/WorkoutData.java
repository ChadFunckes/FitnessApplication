package ismgapps.fitnessapplication;

/**
 * Created by Chad on 11/13/2015.
 */
public class WorkoutData {
    private String name;
    private String description;
    private double cal_count;

    public static final WorkoutData[] workouts = {
            new WorkoutData("The Limb Loosener", "5 Handstand push-ups\n10 1-legged squats\n15 pull-ups", 1.2),
            new WorkoutData("Core Agony", "100 Pull-ups\n100 Push-ups\n100 Sit-ups\n100 Squats", 2.2),
            new WorkoutData("The Wimp Special","5 Pull-ups\n10 Push-ups\n15 Squats", 3.2),
            new WorkoutData("Strength and Length","500 meter run\n21 x 1.5 pood kettlebell swing\n21 x pull-ups", 2.4)
    };

    private WorkoutData(String name, String description, double cal_count){
        this.name = name;
        this.description = description;
        this.cal_count = cal_count;
    }

    public String getDescription(){
        return description;
    }

    public String getName(){
        return name;
    }

    public String toString(){
        return this.name;
    }
}
