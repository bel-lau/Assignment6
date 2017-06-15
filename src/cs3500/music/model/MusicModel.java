package cs3500.music.model;

import static java.util.Arrays.fill;

/**
 * MusicModel is an implementation of the Music Operations.
 */
public class MusicModel implements MusicOperations<Note[][]> {

  private Note[][] piece;
  private final int MAX_LENGTH;
  private int tempo;

  /**
   * Constructor for the MusicModel. Creates 2D array container to
   * contain a music piece with a max length that is given, so as to
   * keep the design flexible but to keep memory usage strict during runtime.
   *
   * @param maxPieceLength    The maximum length of the piece of music
   *                          to be modeled.
   */
  public MusicModel(int maxPieceLength, int tempo) {
    piece = new Note[128][maxPieceLength];
    MAX_LENGTH = maxPieceLength;
    for (int i = 0; i < 120; i++) {
      fill(piece[i],null);
    }
    this.tempo = tempo;
  }

  @Override
  public void addNote(int value, int start, int duration) {
    if (value < 0 || value > 127) {
      throw new IllegalArgumentException("Invalid note value");
    }
    if (start < 0) {
      throw new IllegalArgumentException("Invalid start beat");
    }
    if (duration <= 0) {
      throw new IllegalArgumentException("Invalid duration");
    }
    if (start + duration > MAX_LENGTH) {
      throw new IllegalArgumentException("Note falls out of bounds of the maximum piece length");
    }

    int counter = 1;

    piece[value][start] = new Note(value, duration, true);

    while (counter < duration) {
      piece[value][start + counter] = new Note(value, duration, false);
      counter++;
    }
    while (piece[value][start + counter] != null
        && !(piece[value][start + counter].isOnset())) {
      piece[value][start + counter] = null;
      counter++;
    }
  }

  @Override
  public void removeNote(int value, int start) {
    if (value < 0
        || value > 128) {
      throw new IllegalArgumentException("Invalid note value");
    }
    if (start >= MAX_LENGTH
        || start < 0) {
      throw new IllegalArgumentException("Invalid note start");
    }
    if (piece[value][start] == null
        || !(piece[value][start].isOnset())) {
      throw new IllegalArgumentException("No note onset at given beat and value");
    }

    int dur = piece[value][start].getDuration();

    for (int i = 0; i < dur; i++) {
      piece[value][start + i] = null;
    }
  }

  @Override
  public void combineSimultaeniously(MusicOperations<Note[][]> other) {
    Note[][] addition = other.getContents();

    for (int i = 0; i < 128; i++) {
      for (int j = 0; j < addition[i].length; j++) {
        if (addition[i][j] != null
            && addition[i][j].isOnset()
            && (j + addition[i][j].getDuration()) < MAX_LENGTH) {
          this.addNote(i, j, addition[i][j].getDuration());
        }
      }
    }
  }

  @Override
  public void combineConsecutively(MusicOperations<Note[][]> other) {
    int shift = this.pieceLength();

    Note[][] addition = other.getContents();

    for (int i = 0; i < 128; i++) {
      for (int j = 0; j < addition[i].length; j++) {
        if (addition[i][j] != null
            && addition[i][j].isOnset()
            && (j + shift + addition[i][j].getDuration()) < MAX_LENGTH) {
          this.addNote(i, j + shift, addition[i][j].getDuration());
        }
      }
    }
  }

  @Override
  public Note[][] getContents() {
    Note[][] copy = new Note[120][MAX_LENGTH];
    for (int i = 0; i < 128; i++) {
      copy[i] = piece[i].clone();
    }
    return copy;
  }

  @Override
  public String printPiece() {
    if (this.pieceLength() < 1) {
      throw new IllegalStateException("No notes have been written yet");
    }

    String print = "";

    int leftPadding = String.valueOf(this.pieceLength()).length();

    print = print + spaceBuffer(leftPadding);

    for (int i = minNote(); i <= maxNote(); i++) {
      String currentNote = new Note(i, 1).toString();
      switch (currentNote.length()) {
        case 2: print = print + "  " + currentNote + " ";
        break;
        case 3: print = print + " " + currentNote + " ";
        break;
        default: print = print + " " + currentNote;
        break;
      }
    }

    print = print + "\n";

    for (int i = 0; i < this.pieceLength(); i++) {
      String beat = String.valueOf(i);
      print = print + spaceBuffer(leftPadding - beat.length()) + beat;

      for (int j = minNote(); j <= maxNote(); j++) {
        if (piece[j][i] == null) {
          print = print + "     ";
        } else if (piece[j][i].isOnset()) {
          print = print + "  +  ";
        } else {
          print = print + "  |  ";
        }
      }

      print = print + "\n";
    }

    return print;
  }

  @Override
  public int getTempo() {
    return this.tempo;
  }

  @Override
  public void setTempo(int tempo) {
    this.tempo = tempo;
  }

  /**
   * Returns the current length of the piece,
   * equal to the beat in which the last note is played.
   *
   * @return the current length of the piece.
   */
  private int pieceLength() {
    int max = 0;

    for (int i = 0; i < 128; i++) {
      for (int j = 0; j < MAX_LENGTH; j++) {
        if (piece[i][j] != null
            && (j + 1) > max) {
          max = j + 1;
        }
      }
    }

    return max;
  }

  /**
   * Returns the minimum valued note currently being used in the piece.
   *
   * @return the minimum note value in the piece.
   */
 public int minNote() {
    int min = 128;

    for (int i = 0; i < 128; i++) {
      for (int j = 0; j < MAX_LENGTH; j++) {
        if (piece[i][j] != null
            && i < min) {
          min = i;
        }
      }
    }

    return min;
  }

  /**
   * Returns the maximum valued note currently being used in the piece.
   *
   * @return the maximum note value in the piece.
   */
  public int maxNote() {
    int max = 0;

    for (int i = 0; i < 128; i++) {
      for (int j = 0; j < MAX_LENGTH; j++) {
        if (piece[i][j] != null
            && i > max) {
          max = i;
        }
      }
    }

    return max;
  }

  /**
   * Creates a string made of n spaces.
   *
   * @param n     the number of spaces desired
   * @return the created string of spaces.
   */
  private static String spaceBuffer(int n) {
    if (n < 0) {
      throw new IllegalArgumentException("Buffer must have positive length");
    }

    String buffer = "";

    for (int i = 0; i < n; i++) {
      buffer = buffer + " ";
    }

    return buffer;
  }
}
