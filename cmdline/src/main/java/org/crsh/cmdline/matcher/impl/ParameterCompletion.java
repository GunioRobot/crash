package org.crsh.cmdline.matcher.impl;

import org.crsh.cmdline.EmptyCompleter;
import org.crsh.cmdline.ParameterDescriptor;
import org.crsh.cmdline.matcher.CmdCompletionException;
import org.crsh.cmdline.matcher.tokenizer.Termination;
import org.crsh.cmdline.spi.Completer;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="mailto:julien.viet@exoplatform.com">Julien Viet</a>
 */
class ParameterCompletion extends Completion {

  /** . */
  private final String prefix;

  /** . */
  private final Termination termination;

  /** . */
  private final ParameterDescriptor<?> parameter;

  /** . */
  private final Completer completer;

  ParameterCompletion(String prefix, Termination termination, ParameterDescriptor<?> parameter, Completer completer) {
    this.prefix = prefix;
    this.termination = termination;
    this.parameter = parameter;
    this.completer = completer;
  }

  Map<String, String> complete() throws CmdCompletionException {

    Class<? extends Completer> completerType = parameter.getCompleterType();
    Completer completer = this.completer;

    // Use the most adapted completer
    if (completerType != EmptyCompleter.class) {
      try {
        completer = completerType.newInstance();
      }
      catch (Exception e) {
        throw new CmdCompletionException(e);
      }
    }

    //
    if (completer != null) {
      try {
        Map<String, Boolean> res = completer.complete(parameter, prefix);
        Map<String, String> delimiter = new HashMap<String, String>();
        for (Map.Entry<String, Boolean> entry : res.entrySet()) {
          delimiter.put(entry.getKey(), entry.getValue() ? termination.getEnd() : "");
        }
        return delimiter;
      }
      catch (Exception e) {
        throw new CmdCompletionException(e);
      }
    } else {
      return Collections.emptyMap();
    }
  }
}
