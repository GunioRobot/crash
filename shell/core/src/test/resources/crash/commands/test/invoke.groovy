import org.crsh.command.Description;
import org.crsh.command.ScriptException;
import java.lang.reflect.Method
import org.crsh.cmdline.annotations.Usage
import org.crsh.cmdline.annotations.Argument
import org.crsh.cmdline.annotations.Command;

@Description("Invoke a static method")
public class invoke extends org.crsh.command.CRaSHCommand {

  @Command
  public Object main(
      @Usage("The fqn of the class to resolve")
      @Argument
      String className,
      @Usage("The method to invoke")
      @Argument
      String methodName) throws ScriptException {
    Class<?> clazz = Thread.currentThread().getContextClassLoader().loadClass(className);
    Method m = clazz.getMethod(methodName);
    m.invoke(null);
  }
}