let currentPage = 0;
let currentTable = 'clients';

document.addEventListener('DOMContentLoaded', function () {
    loadData(currentTable, currentPage);

    document.querySelectorAll('.nav-link').forEach(tab => {
        tab.addEventListener('click', function () {
            currentTable = this.getAttribute('data-table');
            currentPage = 0;
            loadData(currentTable, currentPage);
        });
    });

    document.getElementById('itemsPerPage').addEventListener('change', function () {
        loadData(currentTable, 0, this.value);
    });
});

function loadData(table, page = 0, size = 10) {
    fetch(`/database/${table}?page=${page}&size=${size}`)
        .then(response => {
            if (!response.ok) {
                throw new Error('Ошибка загрузки данных');
            }
            return response.text();
        })
        .then(html => {
            document.getElementById('tableContent').innerHTML = html;
        })
        .catch(error => console.error('Ошибка загрузки:', error));
}


function changePage(page) {
    currentPage = page;
    loadData(currentTable, currentPage);
}


function renderPagination(data) {
    const pagination = document.getElementById('pagination');
    pagination.innerHTML = '';

    if (!data.first) {
        pagination.innerHTML += `<li class="page-item">
                                   <a class="page-link" href="#" onclick="loadData('${currentTable}', ${data.number - 1})">Previous</a>
                                 </li>`;
    }

    for (let i = 0; i < data.totalPages; i++) {
        pagination.innerHTML += `<li class="page-item ${i === data.number ? 'active' : ''}">
                                   <a class="page-link" href="#" onclick="loadData('${currentTable}', ${i})">${i + 1}</a>
                                 </li>`;
    }

    if (!data.last) {
        pagination.innerHTML += `<li class="page-item">
                                   <a class="page-link" href="#" onclick="loadData('${currentTable}', ${data.number + 1})">Next</a>
                                 </li>`;
    }
}

// Загрузка данных по умолчанию при загрузке страницы
window.onload = function () {
    loadData('clients');
};