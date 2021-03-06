var charts = 
	{
		drawChart: function(jsonStr){
		
			var json = JSON.parse(jsonStr);
			$('#placeholder').height($('body').height());
			$('#placeholder').width($('body').width());
		
			var plotarea = $("#placeholder");
		   var dataLine=json.TIME_HEARTRATE;
		   var dataLine2=json.TIME_SPEED;
		   var plotData = [];
		   plotData.push({
					data: dataLine.data,
					label: dataLine.Name,
					lines: {show:true, steps:false},
					clickable:true,
					hoverable:false,
					color: "rgb(255, 10, 10)",
					fill:true
				},
				{
					data: dataLine2.data,
					label: dataLine2.Name,
					lines: {show:true, steps:false},
					clickable:true,
					hoverable:false,
					color: "rgb(10, 10, 255)",
					fill:true,
					yaxis:2
				});
			$.plot(plotarea , plotData,
			{
			grid: {
				hoverable: true,
				clickable: true,
				backgroundColor: "rgb(252,252,252)"
			},yaxes: [ {position:"left", min: 0 }, { position: "right", min: 0 }]
			}
		);
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
		},
		showTooltip: function(x, y, contents) {
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
		
	};
