var charts = 
	{
		drawChart: function(jsonStr){
			console.log(jsonStr);
		}
	}


$(document).ready(function(){
	$.getJSON("barLine.json", function(json) {
	   //wenn erfolgreich - starte Plot zu zeichnen:
		   var plotarea = $("#placeholder");
		   var dataBar=json.dataBar;
			var dataLine=json.dataLine;
			$.plot(plotarea , [
				{
					data: dataBar,
					label: "Pace",
					bars: {show: true},
					clickable:true,
					hoverable:false,
					color: "rgb(160, 255, 123)",
					fill:true
				},
				{
					data: dataLine,
					label:"Puls",
					lines: { show: true, steps: false },
					hoverable:false,
					color: "rgb(255, 100, 100)",
					yaxis:2
					
				}               
			],
			{
			grid: {
				hoverable: true,
				clickable: true,
				backgroundColor: "rgb(252,252,252)"
			},yaxes: [ {position:"left", min: 0 }, { position: "right", min: 0 }]
			}
		);
	});

    function showTooltip(x, y, contents) {
        $('<div id="tooltip">' + contents + '</div>').css({
            position: 'absolute',
            display: 'none',
            top: y + 5,
            left: x + 5,
            border: '1px solid #000',
            padding: '2px',
            'background-color': '#eee',
            opacity: 0.80
        }).appendTo("body").fadeIn(200);
    }
    
    var previousPoint = null;
    $("#placeholder").bind("plotclick", function(event, pos, item) {
        $("#x").text(pos.x.toFixed(2));
        $("#y").text(pos.y.toFixed(2));
            if (item) {
                if (previousPoint != item.dataIndex) {
                    previousPoint = item.dataIndex;
                    
                    $("#tooltip").remove();
                    var x = item.datapoint[0].toFixed(2), 
                    y = item.datapoint[1].toFixed(2);
                    
                    showTooltip(item.pageX, item.pageY, 
                    item.series.label + " at Kilometer " + x + " = " + y);
                }
            } 
            else {
                $("#tooltip").remove();
                previousPoint = null;
            }   
    });
});