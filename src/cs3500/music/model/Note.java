package cs3500.music.model;

/**
 * A note represents a single beat in the Music Model implementation.
 * It can be an onset of a tone, or a continuation of an onset.
 * Though it represents a single beat, it stores data of its entire
 * tone.
 *
 * <p>Notes' numerical value represents the musical scale, 0 representing C1
 * all the way to 119 representing B10.</p>
 */
public class Note {
  private final int value;
  private final int duration;
  private final boolean onset;

  /**
   * Constructor for a Note given all information about the note.
   *
   * @param value      value of the note
   * @param duration   duration of the whole tone
   * @param onset      whether or not this object represents the
   *                   onset of a tone.
   */
  public Note(int value, int duration, boolean onset) {
    if (value > 119 || value < 0) {
      throw new IllegalArgumentException("Note value is invalid.");
    }
    this.value = value;
    this.onset = onset;
    this.duration = duration;
  }

  /**
   * If the onset state is not declared, it is assumed to be false.
   *
   * @param value     value of the note
   * @param duration  duration of the whole tone
   */
  public Note(int value, int duration) {
    if (value > 119 || value < 0) {
      throw new IllegalArgumentException("Note value is invalid.");
    }
    if (duration <= 0) {
      throw new IllegalArgumentException("Note duration is invalid");
    }
    this.value = value;
    this.onset = false;
    this.duration = duration;
  }

  /**
   * Determines if this note is an onset.
   *
   * @return the onset value
   */
  public boolean isOnset() {
    return onset;
  }

  /**
   * Determines this note's duration.
   *
   * @return the duration value.
   */
  public int getDuration() {
    return duration;
  }

  /**
   * Converts this note's value to its respective musical note
   * as a string.
   *
   * @return this note's string value
   */
  public String toString() {
    String letter;
    String octave;

    switch (value % 12) {
      case 0: letter = "C";
      break;
      case 1: letter = "C#";
      break;
      case 2: letter = "D";
      break;
      case 3: letter = "D#";
      break;
      case 4: letter = "E";
      break;
      case 5: letter = "F";
      break;
      case 6: letter = "F#";
      break;
      case 7: letter = "G";
      break;
      case 8: letter = "G#";
      break;
      case 9: letter = "A";
      break;
      case 10: letter = "A#";
      break;
      default: letter = "B";
    }

    octave = Integer.toString((value / 12) + 1);

    return letter + octave;
  }
}
