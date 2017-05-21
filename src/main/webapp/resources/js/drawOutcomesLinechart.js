google.charts.load('current', {packages: ['corechart', 'line']});
google.charts.setOnLoadCallback(refreshLineChart());
function drawLineChart(data) {

    var dataTable = new google.visualization.DataTable();

    dataTable.addColumn('number', 'Day');

    for (var type_ in data[1]) {
        dataTable.addColumn('number', type_)
    }

    var numOfTypes = Object.keys(data[1]).length;

    for (var day in data) {
        var dataArr = [numOfTypes+1];
        dataArr[0] = parseInt(day);
        var i = 1;
        for (var type in data[day]) {
            dataArr[i] = (data[day])[type];
            i++;
        }
        dataTable.addRow(dataArr);
    }


    var options = {

        title: "Сумма расходов по категории за день",

        hAxis: {
            title: 'День'
        },
        vAxis: {
            title: 'Сумма'
        },
        series: {
            1: {curveType: 'function'}
        }
    };

    var chart = new google.visualization.LineChart(document.getElementById('linechart'));
    chart.draw(dataTable, options);
}

function refreshLineChart() {
    LoadLineChart($("#linechartForm [name=month]").val(), $("#linechartForm [name=year]").val(),
        $('#linechartForm [name=accountName]').val());
}
