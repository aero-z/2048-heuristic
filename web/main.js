/*jslint browser: true, nomen: true*/
/*global $, GameStateJS, _*/

"use strict";

var game = new GameStateJS();
var proposedMove;

function updateArrows() {
    var normalColour = "#111111",
        proposedColour = "#BB1111",
        moves = ["up", "down", "left", "right"];
    _.each(moves, function (move) {
        var colour = (move === proposedMove) ? proposedColour : normalColour;
        $('button[name=' + move + ']').css("background-color", colour);
    });
}

function updateProposedMove() {
    proposedMove = game.getProposedMove();
    updateArrows();
}

function updateCells() {
    $(".cell_content").each(function (i) {
        var n = game.getCell(Math.floor(i / 4), i % 4),
            f = n / 11,
            r = Math.floor(255 * f),
            g = Math.floor(255 - 255 * f);
        if (n === 0) {
            $(this).css("background-color", "#CCCCCC");
            $(this).text("");
        } else {
            $(this).css("background-color", "rgb(" + r + ", " + g + ", 0)");
            $(this).text(Math.pow(2, n));
        }
    });
}

function update() {
    updateCells();
    updateArrows();
}

function onButtonHeuristic() {
    updateProposedMove();
}

function onButtonMove(move) {
    game = game.move(move);
    proposedMove = undefined;
    update();
}

function onClickCell(isLeftButton, x, y) {
    var value = game.getCell(x, y);
    if (isLeftButton) {
        value += 1;
    } else if (value > 0) {
        value -= 1;
    }
    game = game.setBlock(x, y, value);
    update();
}

function init() {
    $(".cell_content").each(function (i) {
        $(this).on('click', function () {
            onClickCell(true, Math.floor(i / 4), i % 4);
            return true;
        });
        $(this).on('contextmenu', function () {
            onClickCell(false, Math.floor(i / 4), i % 4);
            return false; // prevent default context menu behaviour
        });
    });
    updateCells(game);
}

init();

