// For demo to fit into DataTables site builder...
$(document).ready(function () {
    $('#theData').dataTable({
        autoWidth: false,
        scrollX: true,        // <--- 已加入：開啟水平滾動，讓表格超過版面時正確顯示捲動條
        scrollCollapse: true,
        paging: true,
        pageLength: 10,
        language: {
            info: "Showing _START_ to _END_ of _TOTAL_ entries",
            infoEmpty: "Showing 0 to 0 of 0 entries",
            infoFiltered: "(filtered from _MAX_ total entries)",
            lengthMenu: "Show _MENU_ entries",
            search: "Search:",
            paginate: {
                first: "First",
                last: "Last",
                next: "Next",
                previous: "Previous"
            }
        }
    });
    $('#theData')
        .removeClass('display')
        .addClass('table table-striped table-bordered');

    //alert("Ripple is used to animate the buttons a bit");
    mdc.ripple.MDCRipple.attachTo(document.querySelector('.foo-button'));
    // let table = new DataTable('#myTable');
});