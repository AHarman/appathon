// Adapted from easings.net
public class Easings {
    public static float easeOutExpo(float delta, float currentTime, float totalTime)
    {
        if (currentTime == totalTime) return delta;
        return (float) (delta * (-Math.pow(2, -10 * currentTime/totalTime) + 1));
    }

    public static float easeInOutExpo(float delta, float currentTime, float totalTime)
    {
        if ((currentTime/=totalTime/2) < 1)
            return (float) (delta/2 * Math.pow(2, 10 * (currentTime - 1)) + 1);

        return (float) (delta/2 * (-Math.pow(2, -10 * --currentTime) + 2) + 1);
    }

    public static float easeOutElastic(float delta, float currentTime, float totalTime)
    {
        if (currentTime==0)
            return 1;

        currentTime = currentTime/totalTime;
        if (currentTime==1)
            return 1+delta;

        double p=totalTime*0.5;
        double s = p/4;
        double m = delta*Math.pow(2,-10*currentTime)
        double n = (currentTime*totalTime-s)*(2*Math.PI)/p;

        return (float) (m * Math.sin(n) + delta);
    }

    public static float easeOutBack(float delta, float currentTime, float totalTime)
    {
        double s = 1.70158;
        return (float) (delta*((currentTime=currentTime/totalTime-1)*currentTime*((s+1)*currentTime + s) + 1) + 1);
    }
}
