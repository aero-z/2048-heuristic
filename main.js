"use strict";

var game = new GameStateJS()

function onClickCell(x,y) {
  if (game.getCell(x,y) === 0) {
    game = game.setBlock(x,y,2);
  }
  update();
}

function onButtonMove(move) {
  game = game.move(move);
  update();
}

function update() {
  updateProposedMove();
  updateCells();
}

function updateProposedMove() {
  var proposedMove = game.getProposedMove();
  var normalColour = "#111111";
  var proposedColour = "#BB1111";
  var moves = ["up", "down", "left", "right"];
  _.each(moves, function(move) {
    var colour = (move === proposedMove) ? proposedColour : normalColour;
    $('button=[name='+move+']').css("background-color", colour);
  })
}

function updateCells() {
  $(".cell_content").each(function(i) {
    var n = game.getCell(Math.floor(i/4), i % 4);
    if (n === 0) {
      $(this).css("background-color", "#CCCCCC");
      $(this).text("");
    }
    else {
      var f = (Math.log(n)/Math.LN2 - 1) / 10;
      var r = Math.floor(255 * f);
      var g = Math.floor(255 - 255 * f);
      $(this).css("background-color", "rgb(" + r + ", " + g + ", 0)");
      $(this).text(n);
    }
  });
}

function init() {
  $(".cell_content").each(function(i) {
    $(this).click(onClickCell.bind(null, Math.floor(i/4), i % 4));
  });
  updateCells(game);
}

init();

