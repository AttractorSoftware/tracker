/**
 * Created by ru-d on 8/7/14.
 */

//Form Validator
jQuery(document).ready(function () {
    checkFields();
    $('#fromDMY').change(function(){
        checkFields();
    });

    $('#toDMY').change(function(){
        checkFields();
    });
});
function checkFields(){
    if($('#fromDMY').val()!=0 && $('#toDMY').val()!=0){
        $('#build-report').prop('disabled', false);
    }
    else{
        $('#build-report').prop('disabled', true);
    }
}
function showInputValues(){
    $("input#fromDMY").val($('#from').text());
    $("input#toDMY").val($('#to').text());
}

//Date Switcher
jQuery(document).ready(function () {
    var form = $("#date-form");

    // Next Day Link
    $('.btn-next').click(function () {
        $('#fromDMY').css('text-indent', '-999em');
        form.find("input#fromDMY").val(getNextDate());
        form.submit();

    })

    // Previous Day Link
    $('.btn-prev').click(function () {
        $('#fromDMY').css('text-indent', '-999em');
        form.find("input#fromDMY").val(getPreviousDate());
        form.submit();
    });

    $( ".report-result" ).click(function() {
        var id = $(this).text().replace(/^\s*|\s*\(.*?\)\s*/g, "");
        $("#"+id+"").toggle('blind');
    });

    $('#fromDMY').datepicker({dateFormat:'dd-mm-yy'});
    $('#toDMY').datepicker({dateFormat:'dd-mm-yy'});
});

function getCurrentDate() {
    var picker = $(".date-display").text();
    return new Date("01 " + picker);
}

function getNextDate() {
    var date = getCurrentDate();
    date.setMonth(date.getMonth() + 1);
    return $.datepicker.formatDate("dd-mm-yy", date);
}

function getPreviousDate() {
    var date = getCurrentDate();
    date.setMonth(date.getMonth() - 1);
    return $.datepicker.formatDate("dd-mm-yy", date)
}

function hideSwitcher(){
    $("#date-switcher").hide();
}
