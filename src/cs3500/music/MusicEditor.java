package cs3500.music;

import cs3500.music.model.MusicModel;
import cs3500.music.view.GuiViewFrame;
import cs3500.music.view.MidiViewImpl;
import cs3500.music.view.ViewType;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;


public class MusicEditor {
  public static void main(String[] args) throws IOException, InvalidMidiDataException, MidiUnavailableException {

    ViewType view;
    if (args.length < 2) {
      throw new IllegalArgumentException("Music file and view type arguments required.");
    }
    /*
    //File f = new File(args[0]);

    Path p = Paths.get(args[0]);

    if (Files.exists(p)) {
      throw new IllegalArgumentException("Invalid txt: " + p.toString());
    }
    //FileReader fr = new FileReader(args[0]);
    */

    try {
      view = ViewType.stringToViewType(args[1]);
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException(e);
    }

    //Call factory class

    MusicModel model = new MusicModel(200, 1);

    model.addNote(30, 0, 2);
    model.addNote(35, 0, 8);
    model.addNote(30, 8, 4);

    GuiViewFrame view = new GuiViewFrame(model);
    MidiViewImpl midiView = new MidiViewImpl();
    // You probably need to connect these views to your model, too...

    view.initialize();
  }
}
