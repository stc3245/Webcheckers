package com.webcheckers.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static com.webcheckers.model.Piece.ColorEnum.RED;

@Tag("Model-tier")
public class PieceTest {

    @Test
    public void testGetType() {
        Piece p = new Piece(BoardView.PieceEnum.SINGLE, RED);
        assertSame(p.getType(), BoardView.PieceEnum.SINGLE);
    }

    @Test
    public void testGetColor() {
        Piece p = new Piece(BoardView.PieceEnum.SINGLE, RED);
        assertSame(p.getColor(), Piece.ColorEnum.RED);
    }

}
