package cs3500.music.view;

import cs3500.music.model.MusicModel;
import cs3500.music.model.Note;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

import javax.swing.*;

/**
 * A dummy view that simply draws a string 
 */
public class ConcreteGuiViewPanel extends JPanel {

  private MusicModel piece;
  private final int startMeasure;
  private Note[][] contents;
  private final int cursor;

  public ConcreteGuiViewPanel (MusicModel model, int cursor) {
    this.piece = model;
    int start = 0;
    while (cursor >= start + 36) {
      start += 36;
    }
    this.startMeasure = start;
    this.contents = piece.getContents();
    this.cursor = cursor;
  }

  @Override
  public void paintComponent(Graphics g){
    // Handle the default painting
    super.paintComponent(g);
    // Look for more documentation about the Graphics class,
    // and methods on it that may be useful
    for (int j = 0; j < 9; j++) {
      g.drawString(new Integer(startMeasure + j * 4).toString(), 75 + 100 * j, 25);
    }

    for (int i = 0; i <= piece.maxNote() - piece.minNote() + 1; i++) {
      g.drawLine(75, 35 + i * 25, 975, 35 + i * 25 );
    }

    for (int i = 0; i <= 9; i++) {
      g.drawLine(75 + 100 * i, 35, 75 + 100 * i, (piece.maxNote() - piece.minNote()) * 25 + 60 );
    }
    for (int i = 0; i <= piece.maxNote() - piece.minNote(); i++) {
      g.drawString(new Note(i + piece.minNote(), 1).toString(), 25, (50 + 25 * i));
    }

    for (int i = 0; i <= piece.maxNote() - piece.minNote(); i++) {
      for (int j = 0; j < 36; j++) {
        if (contents[i + piece.minNote()][j + startMeasure] != null
            && contents[i + piece.minNote()][j + startMeasure].isOnset()) {
          g.setColor(Color.BLACK);
          g.fillRect(75 + 25 * j, 35 + (i * 25), 25, 25);
        } else if(contents[i + piece.minNote()][j + startMeasure] != null) {
          g.setColor(Color.GREEN);
          g.fillRect(75 + 25 * j, 36 + (i * 25), 25, 24);
        }
      }
    }

    g.setColor(Color.RED);
    g.drawLine(75 + 25 * (cursor - startMeasure), 35, 75 + 25 * (cursor - startMeasure), 35 + (piece.maxNote() - piece.minNote() + 1) * 25);
  }


}
