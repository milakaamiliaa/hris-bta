function dropdownChangePresensi() {
    var input, filter, table, tr, td, i;
    input = document.getElementById("mylist");
    filter = input.value.toUpperCase();
    table = document.getElementById("dataTable");
    tr = table.getElementsByTagName("tr");
    for (i = 0; i < tr.length; i++) {
        td = tr[i].getElementsByTagName("td")[1];
        if (td) {
            if (td.innerHTML.toUpperCase().indexOf(filter) >-1){
                tr[i].style.display = "";
            }
            else if (filter == ""){
                tr[i].style.display = "";
            }else {
                tr[i].style.display = "none";
            }
        }
    }
}

function dropdownChangeGaji() {
    var input, filter, table, tr, td, i;
    input = document.getElementById("mylist");
    filter = input.value.toUpperCase();
    table = document.getElementById("dataTable");
    tr = table.getElementsByTagName("tr");
    for (i = 0; i < tr.length; i++) {
        td = tr[i].getElementsByTagName("td")[1];
        if (td) {
            if (td.innerHTML.toUpperCase().indexOf(filter) >-1){
                tr[i].style.display = "";
            }
            else if (filter == ""){
                tr[i].style.display = "";
            }else {
                tr[i].style.display = "none";
            }
        }
    }
}

function dropdownChangeGaji2() {
    var input, filter, table, tr, td, i;
    input = document.getElementById("mylist2");
    filter = input.value.toUpperCase();
    table = document.getElementById("dataTable2");
    tr = table.getElementsByTagName("tr");
    for (i = 0; i < tr.length; i++) {
        td = tr[i].getElementsByTagName("td")[2];
        if (td) {
            if (td.innerHTML.toUpperCase().indexOf(filter) >-1){
                tr[i].style.display = "";
            }
            else if (filter == ""){
                tr[i].style.display = "";
            }else {
                tr[i].style.display = "none";
            }
        }
    }
}