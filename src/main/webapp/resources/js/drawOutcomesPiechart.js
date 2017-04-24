function drawChart(data2) {
    // Define the chart to be drawn.
    var data = new google.visualization.DataTable();
    data.addColumn('string', 'Type');
    data.addColumn('number', 'Amount');
    for (var key in data2) {
        data.addRow([key, data2[key]]);
    }
    // Set chart options
    var options = {
        'title': 'Statistics',
        'width': 550,
        'height': 400
    };

    // Instantiate and draw the chart.
    var chart = new google.visualization.PieChart(document.getElementById('container'));
    chart.draw(data, options);
}
google.charts.setOnLoadCallback(drawChart);