package cs3500.music.view;

/**
 * Created by Lauren Bell on 6/14/2017.
 */
public enum ViewType {
  console, visual, midi;

  public static ViewType stringToViewType(String str) throws IllegalArgumentException {
    if (str.equalsIgnoreCase("console")) {
      return console;
    } else if (str.equalsIgnoreCase("visual")) {
      return visual;
    } else if (str.equalsIgnoreCase("midi")) {
      return midi;
    } else {
      throw new IllegalArgumentException("Invalid view type");
    }
  }


}
