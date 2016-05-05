package es.uvigo.esei.daa.letta.entities;

import org.junit.Test;
import java.text.ParseException;
import static org.junit.Assert.assertThat;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;


public class AssistUnitTest {

    @Test

    public void testStringInt(){
        try{
            String usr = "user1";
            int evt = 3;
            Assist assist = new Assist (usr, evt);

            assertThat(assist.getUser_id(), is(equalTo(usr)));
            assertThat(assist.getEvent_id(), is(equalTo(evt)));
        }
        catch()(ParseException e) {
            e.printStackTrace();
        }
    }

    @Test

    public void testSetUser_id(){
        try{
            String usr = "user1";
            int evt = 3;
            Assist assist = new Assist (usr, evt);

            assist.setUser_id("userX");
            assertThat(assist.getUser_id(), is(equalTo("userX")));
            assertThat(assist.getEvent_id(), is(equalTo(evt)));
        }
        catch()(ParseException e) {
            e.printStackTrace();
        }
    }

    @Test

    public void testSetEvent_id(){
        try{
            String usr = "user1";
            int evt = 3;
            Assist assist = new Assist (usr, evt);

            assist.setEvent_id(130);
            assertThat(assist.getUser_id(), is(equalTo(usr)));
            assertThat(assist.getEvent_id(), is(equalTo(130)));
        }

        catch()(ParseException e) {
            e.printStackTrace();
        }
    }
}
