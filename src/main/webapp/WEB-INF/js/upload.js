$(document).ready(function(){
  $("#submit").click(function() {
    var data = $("#text").val();
    msgpack.upload("/api/upload", { "data": data }, function(text) {
      $("#result").text(text);
    });
  });
});