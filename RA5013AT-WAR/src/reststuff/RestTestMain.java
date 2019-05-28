package reststuff;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/* 5.27.2019.
 * 
 * VAZNO!!!
 * ApplicationPath ne moze da ti bude "/".
 * Moras staviti nesto drugo (npr "/service" ili "/rest" ili nesto tako)
 * 
 */
@ApplicationPath("/rest")
public class RestTestMain extends Application {

}


