google.charts.load('current', {packages: ['corechart']});
google.charts.setOnLoadCallback(refreshChart);
function drawChart(data) {
    // Define the chart to be drawn.
    var dataTable = new google.visualization.DataTable();
    dataTable.addColumn('string', 'Type');
    dataTable.addColumn('number', 'Amount');
    for (var key in data) {
        dataTable.addRow([key, data[key]]);
    }
    // Set chart options
    var options = {
        title: 'Сумма расходов по категориям за период',
        width: 550,
        height: 400,
        backgroundColor: 'transparent'
    };

    // Instantiate and draw the chart.
    var chart = new google.visualization.PieChart(document.getElementById('piechart'));
    chart.draw(dataTable, options);
}

function refreshChart() {
    LoadChart($("#rangeForm [name=start]").val(), $("#rangeForm [name=end]").val(),
        $('#rangeForm [name=accountName]').val());
}
