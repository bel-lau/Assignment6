import cs3500.music.model.MusicModel;
import cs3500.music.model.MusicOperations;
import cs3500.music.model.Note;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Tests of the MusicModel class.
 */
public class MusicModelTest {

  MusicOperations<Note[][]> model = new MusicModel(200, 1) ;
  MusicOperations<Note[][]> model2 = new MusicModel(200, 1);

  /**
   * Attempt to print an piece with no notes in it.
   */
  @Test (expected = IllegalStateException.class)
  public void testInvalidPrint() {
    model.printPiece();
  }

  /**
   * Test print of a piece with 1 note.
   */
  @Test
  public void testPrintOne() {
    model.addNote(50, 1, 3);
    assertEquals(
        "   D5 \n" +
        "0     \n" +
        "1  +  \n" +
        "2  |  \n" +
        "3  |  \n",model.printPiece());
  }

  /**
   * Test print of a piece with 2 notes.
   */
  @Test
  public void testPrintTwo() {
    model.addNote(119, 1, 3);
    model.addNote(110, 4, 2);
    assertEquals(
        "  D10  D#10 E10  F10  F#10 G10  G#10 A10  A#10 B10 \n" +
        "0                                                  \n" +
        "1                                               +  \n" +
        "2                                               |  \n" +
        "3                                               |  \n" +
        "4  +                                               \n" +
        "5  |                                               \n", model.printPiece());
  }

  /**
   * Tests that the print function makes row numbering right-justified
   * by testing with a piece with a length with 2 digits.
   */
  @Test
  public void testPrintDigitBuffer() {
    model.addNote(30, 0, 2);
    model.addNote(35, 0, 8);
    model.addNote(30, 8, 4);
    assertEquals(
        "   F#3   G3  G#3   A3  A#3   B3 \n" +
        " 0  +                        +  \n" +
        " 1  |                        |  \n" +
        " 2                           |  \n" +
        " 3                           |  \n" +
        " 4                           |  \n" +
        " 5                           |  \n" +
        " 6                           |  \n" +
        " 7                           |  \n" +
        " 8  +                           \n" +
        " 9  |                           \n" +
        "10  |                           \n" +
        "11  |                           \n", model.printPiece());
  }

  /**
   * Test adding a note of too small a value.
   */
  @Test (expected = IllegalArgumentException.class)
  public void testAddLowValue() {
    model.addNote(-1, 0, 1);
  }

  /**
   * Test adding a note of too high a value.
   */
  @Test (expected = IllegalArgumentException.class)
  public void testAddHighValue() {
    model.addNote(120, 0, 1);
  }

  /**
   * Test adding a note with a negative start beat.
   */
  @Test (expected = IllegalArgumentException.class)
  public void testAddInvalidStart() {
    model.addNote(40, -2, 1);
  }

  /**
   * Test adding a note with a duration of 0.
   */
  @Test (expected = IllegalArgumentException.class)
  public void testAddInvalidDuration() {
    model.addNote(40, 0, 0);
  }

  /**
   * Test adding a note that is out of bounds of the max length of
   * the piece.
   */
  @Test (expected = IllegalArgumentException.class)
  public void testAddOutOfBounds() {
    model.addNote(40, 190, 20);
  }

  /**
   * Test trying to remove a note of too low a value.
   */
  @Test (expected = IllegalArgumentException.class)
  public void testRemoveLowValue() {
    model.removeNote(-3, 0);
  }

  /**
   * Test trying to remove a note of too high a value.
   */
  @Test (expected = IllegalArgumentException.class)
  public void testRemoveHighValue() {
    model.removeNote(121, 0);
  }

  /**
   * Test trying to remove a note at a negative start beat.
   */
  @Test (expected = IllegalArgumentException.class)
  public void testRemoveLowStart() {
    model.removeNote(40, -2);
  }

  /**
   * Test trying to remove a note at a start beat out of bounds of
   * max piece length.
   */
  @Test (expected = IllegalArgumentException.class)
  public void testRemoveHighStart() {
    model.removeNote(40, 200);
  }

  /**
   * Test trying to remove a note that does not exist.
   */
  @Test (expected = IllegalArgumentException.class)
  public void testRemoveNonexistant() {
    model.removeNote(40, 0);
  }

  /**
   * Test trying to remove a note at a negative start beat.
   */
  @Test (expected = IllegalArgumentException.class)
  public void testRemoveNotOnset() {
    model.addNote(40, 0, 2);
    model.removeNote(40, 1);
  }

  /**
   * Test successfully removing the only note in the piece.
   */
  @Test (expected = IllegalStateException.class)
  public void testRemoveSuccessful() {
    model.addNote(40, 0, 2);
    model.removeNote(40, 0);
    assertEquals("",model.printPiece());
  }

  /**
   * Test successfully simultaneously combining two pieces.
   */
  @Test
  public void testSimultaneous() {
    model.addNote(40, 0, 2);
    model.addNote(42, 4, 4);
    model2.addNote(41, 0, 4);
    model2. addNote(42, 0, 2);
    model.combineSimultaeniously(model2);
    assertEquals(
        "   E4   F4  F#4 \n" +
        "0  +    +    +  \n" +
        "1  |    |    |  \n" +
        "2       |       \n" +
        "3       |       \n" +
        "4            +  \n" +
        "5            |  \n" +
        "6            |  \n" +
        "7            |  \n", model.printPiece());
  }

  /**
   * Test that successfully simultaneously adding model2 to model
   * does not cause mutation of model2.
   */
  @Test
  public void testSimultaneousMutation() {
    model.addNote(40, 0, 2);
    model.addNote(42, 4, 4);
    model2.addNote(41, 0, 4);
    model2. addNote(42, 0, 2);
    model.combineSimultaeniously(model2);
    assertEquals(
        "   F4  F#4 \n" +
            "0  +    +  \n" +
            "1  |    |  \n" +
            "2  |       \n" +
            "3  |       \n", model2.printPiece());
  }

  /**
   * Test successfully consecutively combining two piece.
   */
  @Test
  public void testConsecutive() {
    model.addNote(40, 0, 2);
    model.addNote(42, 4, 4);
    model2.addNote(41, 0, 4);
    model2. addNote(42, 0, 2);
    model.combineConsecutively(model2);
    assertEquals(
        "    E4   F4  F#4 \n" +
            " 0  +            \n" +
            " 1  |            \n" +
            " 2               \n" +
            " 3               \n" +
            " 4            +  \n" +
            " 5            |  \n" +
            " 6            |  \n" +
            " 7            |  \n" +
            " 8       +    +  \n" +
            " 9       |    |  \n" +
            "10       |       \n" +
            "11       |       \n", model.printPiece());
  }

  /**
   * Test that successfully consecutively adding model2 to model
   * does not cause mutation of model2.
   */
  @Test
  public void testConsecutiveMutation() {
    model.addNote(40, 0, 2);
    model.addNote(42, 4, 4);
    model2.addNote(41, 0, 4);
    model2. addNote(42, 0, 2);
    model.combineConsecutively(model2);
    assertEquals(
        "   F4  F#4 \n" +
            "0  +    +  \n" +
            "1  |    |  \n" +
            "2  |       \n" +
            "3  |       \n", model2.printPiece());
  }
}