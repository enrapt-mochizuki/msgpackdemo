$(document).ready(function(){
  $("#submit").click(function() {
    var data = $("#text").val();
    msgpack.upload("/msgpackuploaddemo", { "data": data }, function(text) {
      $("#result").text(text);
    });
  });
});