/*jslint browser: true, nomen: true*/
/*global $, GameStateJS, _*/

"use strict";

var game = new GameStateJS();

function updateProposedMove() {
    var proposedMove = game.getProposedMove(),
        normalColour = "#111111",
        proposedColour = "#BB1111",
        moves = ["up", "down", "left", "right"];
    _.each(moves, function (move) {
        var colour = (move === proposedMove) ? proposedColour : normalColour;
        $('button=[name=' + move + ']').css("background-color", colour);
    });
}

function updateCells() {
    $(".cell_content").each(function (i) {
        var n = game.getCell(Math.floor(i / 4), i % 4),
            f = (Math.log(n) / Math.LN2 - 1) / 10,
            r = Math.floor(255 * f),
            g = Math.floor(255 - 255 * f);
        if (n === 0) {
            $(this).css("background-color", "#CCCCCC");
            $(this).text("");
        } else {
            $(this).css("background-color", "rgb(" + r + ", " + g + ", 0)");
            $(this).text(n);
        }
    });
}

function update() {
    updateProposedMove();
    updateCells();
}

function onButtonMove(move) {
    game = game.move(move);
    update();
}

function onClickCell(x, y) {
    if (game.getCell(x, y) === 0) {
        game = game.setBlock(x, y, 2);
    }
    update();
}

function init() {
    $(".cell_content").each(function (i) {
        $(this).click(onClickCell.bind(null, Math.floor(i / 4), i % 4));
    });
    updateCells(game);
}

init();

