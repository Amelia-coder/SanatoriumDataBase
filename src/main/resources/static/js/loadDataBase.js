let currentPage = 0;
let currentSize = 10;
let currentTable = 'clients';
let totalElements = 0;
let currentSearchQuery = '';

document.addEventListener('DOMContentLoaded', function () {
    loadData(currentTable, currentPage, currentSize, currentSearchQuery);

    // Обработчики для смены вкладок
    document.querySelectorAll('.nav-link').forEach(tab => {
        tab.addEventListener('click', function () {
            currentTable = this.getAttribute('data-table');
            currentPage = 0;
            currentSearchQuery = '';

            const currentSelect = document.querySelector(`#itemsPerPage${capitalize(currentTable)}`);
            currentSize = parseInt(currentSelect.value);

            loadData(currentTable, currentPage, currentSize, currentSearchQuery);
        });
    });


    // Обработчики для изменения itemsPerPage
    document.querySelectorAll('[id^="itemsPerPage"]').forEach(select => {
        select.addEventListener('change', function () {
            const tableName = this.id.replace('itemsPerPage', '').toLowerCase();
            if (tableName === currentTable) {
                currentSize = parseInt(this.value);
                const totalPages = Math.ceil(totalElements / currentSize);
                if (currentPage >= totalPages) {
                    currentPage = totalPages - 1;
                }
                loadData(currentTable, currentPage, currentSize, currentSearchQuery);
            }
        });
    });

    // Обработчик для поиска
    document.querySelectorAll('.btn-primary').forEach(button => {
        button.addEventListener('click', function () {
            const input = this.previousElementSibling;
            currentSearchQuery = input.value.trim();
            currentPage = 0;
            loadData(currentTable, currentPage, currentSize, currentSearchQuery);
        });
    });
});


// Вспомогательная функция для преобразования строки в заглавный регистр
function capitalize(str) {
    return str.charAt(0).toUpperCase() + str.slice(1);
}

// Функция загрузки данных
function loadData(table, page = 0, size = 10, searchQuery = '') {
    const url = `/database/${table}?page=${page}&size=${size}&search=${encodeURIComponent(searchQuery)}`;
    console.log(`Загрузка данных: ${url}`);

    fetch(url)
        .then(response => {
            if (!response.ok) {
                throw new Error('Ошибка загрузки данных');
            }
            return response.json();
        })
        .then(data => {
            totalElements = data.totalElements;
            const tableContentId = `${table}TableContent`;
            const targetElement = document.getElementById(tableContentId);
            if (targetElement) {
                targetElement.innerHTML = data.contentHtml;
            } else {
                console.error(`Элемент с ID "${tableContentId}" не найден.`);
            }
            renderPagination(data, table);
        })
        .catch(error => console.error('Ошибка загрузки:', error));
}

// Рендеринг пагинации
function renderPagination(data, table) {
    const paginationId = `${table}Pagination`;
    const pagination = document.getElementById(paginationId);
    pagination.innerHTML = '';

    if (!data.first) {
        pagination.innerHTML += `<li class="page-item">
                                   <a class="page-link" href="#" onclick="changePage(${data.number - 1})">Previous</a>
                                 </li>`;
    }

    pagination.innerHTML += `<li class="page-item">
                                <select id="pageSelect" class="form-select" onchange="changePage(parseInt(this.value))">
                                    ${Array.from({ length: data.totalPages }, (_, i) => `<option value="${i}" ${i === data.number ? 'selected' : ''}>${i + 1}</option>`).join('')}
                                </select>
                             </li>`;

    if (!data.last) {
        pagination.innerHTML += `<li class="page-item">
                                   <a class="page-link" href="#" onclick="changePage(${data.number + 1})">Next</a>
                                 </li>`;
    }
}


// Функция поиска данных в текущей таблице
function searchTable(query) {
    const url = '/database/${currentTable}/search?query=${encodeURIComponent(query)}';
    fetch(url)
        .then(response => {
            if (!response.ok) {
                throw new Error('Ошибка поиска');
            }
            return response.json();
        })
        .then(data => {
            const tableContentId = '${currentTable}TableContent';
            const targetElement = document.getElementById(tableContentId);
            if (targetElement) {
                targetElement.innerHTML = generateSearchResultsHtml(data);
            } else {
                console.error('Элемент с ID "${tableContentId}" не найден.');
            }
            const paginationId = '${currentTable}Pagination';
            document.getElementById(paginationId).innerHTML = '';
        })
        .catch(error => console.error('Ошибка поиска:', error));
}

// Генерация HTML для результатов поиска
function generateSearchResultsHtml(data) {
    return data.length > 0
        ? data.map(item => `
            <tr>
                <td>${item.id}</td>
                <td>${item.firstName}</td>
                <td>${item.lastName}</td>
                <td>
                    <button class='btn btn-warning btn-sm' onclick='editItem(${item.id})'>Edit</button>
                    <button class='btn btn-danger btn-sm' onclick='deleteItem(${item.id})'>Delete</button>
                </td>
            </tr>
        `).join('')
        : '<tr><td colspan="4">Ничего не найдено</td></tr>';
}

// // Функции редактирования и удаления
// function editItem(id) {
//     console.log(Edit item with ID: ${id});
//     // Логика редактирования
// }

// function deleteItem(id) {
//     if (confirm('Вы уверены, что хотите удалить этот элемент?')) {
//         fetch(/database/${currentTable}/${id}, { method: 'DELETE' })
//             .then(response => {
//                 if (!response.ok) {
//                     throw new Error('Ошибка удаления элемента');
//                 }
//                 loadData(currentTable, currentPage, currentSize, currentSearchQuery);
//             })
//             .catch(error => console.error('Ошибка удаления:', error));
//     }
// }

// Функция изменения страницы
function changePage(page) {
    currentPage = page;
    loadData(currentTable, currentPage, currentSize, currentSearchQuery);
}


