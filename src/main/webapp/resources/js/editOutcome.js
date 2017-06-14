$(document).ready(function () {

    $('.hashtagsSelect').hide();
    $('.outcometypeSelect').hide();
    $('.date').hide();

    $('.editOutcomeBtn').click(function (e) {
        var editDiv = $(this).closest('.editOutcomeDiv');

        editDiv.find(".acceptOutcomeBtn").show();
        editDiv.find(".cancelOutcomeBtn").show();
        $(this).hide();

        var tableRow = $(this).closest('.tableRow');

        var note = tableRow.find(".note");
        note.attr('contenteditable', 'true');
        note.focus();
        var oldNoteVal = $(this).closest('.editOutcomeDiv').find('.oldNoteVal');
        oldNoteVal.val(note.text());

        var outcometype = tableRow.find(".outcometype");
        outcometype.hide();
        var outcometypeSelect = tableRow.find(".outcometypeSelect");
        outcometypeSelect.select2({
            width: '100%',
            theme: "bootstrap",
        });
        outcometypeSelect.show();

        var hashtags = tableRow.find(".hashtags");
        hashtags.hide();
        var hashtagsSelect = tableRow.find(".hashtagsSelect");
        hashtagsSelect.select2({
            placeholder: "Хэштеги",
            tags: true,
            width: '100%',
            theme: "bootstrap",
            tokenSeparators: [',', ' ', '#']
        });
        hashtagsSelect.show();

        var outcomeDate = tableRow.find(".outcomeDate");
        outcomeDate.hide();
        var date = tableRow.find(".date");
        date.show();
    });

    $('.acceptOutcomeBtn').click(function (e) {
        e.preventDefault();

        var tableRow = $(this).closest('.tableRow');
        var note = tableRow.find(".note").text();
        tableRow.find('.newNote').val(note);

        var form = tableRow.find(".editOutcomeForm");
        var data = form.serializeArray();
        var objectifyData = serializeObject(data);
        var jsonData = JSON.stringify(objectifyData);
        $.ajax({
            type: 'POST',
            beforeSend: function (request) {
                var token = $("meta[name='_csrf']").attr("content");
                var header = $("meta[name='_csrf_header']").attr("content");
                request.setRequestHeader(header, token);
            },
            contentType: 'application/json; charset=UTF-8',
            url: form.attr('action'),
            data: jsonData
        }).done(function (data) {
            location.reload();
        })
    });

    $('.cancelOutcomeBtn').click(function (e) {
        e.preventDefault();
        var editDiv = $(this).closest('.editOutcomeDiv');
        stopEdit(editDiv);
    });

    function stopEdit(editDiv) {

        var tableRow = editDiv.find(".acceptOutcomeBtn").closest('.tableRow');
        var note = tableRow.find(".note");
        note.attr('contenteditable', 'false');
        var oldNote = tableRow.find('.oldNote').val().trim();
        note.text(oldNote);

        var select2 = tableRow.find(".select2");
        select2.hide();

        var outcometype = tableRow.find(".outcometype");
        outcometype.show();

        var hashtags = tableRow.find(".hashtags");
        hashtags.show();

        var outcomeDate = tableRow.find(".outcomeDate");
        outcomeDate.show();
        var date = tableRow.find(".date");
        date.hide();

        editDiv.find(".acceptOutcomeBtn").hide();
        editDiv.find(".cancelOutcomeBtn").hide();
        editDiv.find(".editOutcomeBtn").show();
    }

    function serializeObject(formArray) {
        var obj = {};
        $.each(formArray, function () {
            var value = this.value || '';
            if (/^\d+$/.test(value))
                value = +value;

            if (obj[this.name] !== undefined) {
                if (!obj[this.name].push) {
                    obj[this.name] = [obj[this.name]];
                }
                obj[this.name].push(value);
            } else {
                obj[this.name] = value;
            }
        });
        return obj;
    };
});
