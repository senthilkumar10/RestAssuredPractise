package karate;


import com.intuit.karate.junit5.Karate;

public class Test {

    @Karate.Test
    Karate testCreateIncident(){
        return Karate.run().feature("classpath:karate/createIncident.feature");
    }
}
