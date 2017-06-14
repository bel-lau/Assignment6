package cs3500.music.model;

/**
 * The MusicOperations interface represents the interface for the MusicModel.
 * It details the methods the MusicModel should implement.
 */
public interface MusicOperations<K> {

  /**
   * Adds a single note to the piece of music.
   *
   * @param value     the scalar value of the note
   * @param start     the starting beat of the note
   * @param duration  the duration of the note
   */
  public void addNote(int value, int start, int duration);

  /**
   * Removes a single note from the piece of music.
   *
   * @param value     the value of the note
   * @param start     the starting beat of the note
   */
  public void removeNote(int value, int start);

  /**
   * Adds all notes from the given piece of music to this
   * piece of music, so that the other piece is simultaneous to
   * this piece.
   *
   * @param other     the piece of music to be added
   */
  public void combineSimultaeniously(MusicOperations<K> other);

  /**
   * Adds all notes from the given piece of music to this piece
   * of music after the end of this piece, so that the other piece
   * is consecutive to this piece.
   *
   * @param other     the piece of music to be added
   */
  public void combineConsecutively(MusicOperations<K> other);

  /**
   * Returns the contents of the piece in the form of the
   * implementations chosen container.
   *
   * @return a copy of the contents of the piece.
   */
  public K getContents();

  /**
   * Returns a string representation of the piece.
   *
   * @return visual representation of the piece.
   */
  public String printPiece();


  public int getTempo();

  public void setTempo(int tempo);
}
