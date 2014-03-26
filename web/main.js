
var game = new GameStateJS()

function onClickCell(x,y) {
  if (game.getCell(x,y) === 0) {
    game = game.setBlock(x,y,2);
  }
  updateCells(game);
}

function onButtonMove(move) {
  game = game.move(move);
  updateCells(game);
}

function updateCells(game) {
  $(".cell_content").each(function(i) {
    var n = game.getCell(Math.floor(i/4), i % 4);
    if (n === 0) {
      $(this).removeClass("cell_content_block");
      $(this).text("");
    }
    else {
      $(this).addClass("cell_content_block");
      $(this).text(n);
    }
  });
}

function init() {
  $(".cell_content").each(function(i) {
    $(this).click(onClickCell.bind(null, Math.floor(i/4), i % 4));
  });
}

init()

