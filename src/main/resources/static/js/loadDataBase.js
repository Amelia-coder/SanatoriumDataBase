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
            document.querySelector("#assignment").className = "tab-pane fade";
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

    document.querySelectorAll('.column-toggle input[type="checkbox"]').forEach(checkbox => {
        checkbox.addEventListener('change', function () {
            const columnClass = this.value;
            const isVisible = this.checked;
            document.querySelectorAll(`.${columnClass}`).forEach(cell => {
                cell.style.display = isVisible ? '' : 'none';
            });
        });
    });

    document.addEventListener('click', (event) => {
        if (event.target.matches('.btn-success')) {
            const ticketId = event.target.getAttribute("data-ticketId");
            let splittedClassName = document.querySelector("#ticket").className.split(" ");
            document.querySelector('#ticket').className = splittedClassName[0] + " " + splittedClassName[1];
            document.querySelector("#assignment").className += " " + splittedClassName[2] + " " + splittedClassName[3];
            loadData("assignments", 0, 10, ticketId);
        }
        // Обработка кнопки редактирования
        if (event.target.matches('.btn-warning')) {
            const rowId = event.target.getAttribute('data-id');
            const tableName = currentTable;

            // Пример получения данных для редактирования (можно улучшить):
            const updatedData = {
                firstName: document.querySelector('#firstNameInput').value,
                lastName: document.querySelector('#lastNameInput').value,
                phone: document.querySelector('#phoneInput').value,
            };

            editRow(tableName, rowId, updatedData);
        }

        // Обработка кнопки удаления
        if (event.target.matches('.btn-danger')) {
            const rowId = event.target.getAttribute('data-id');
            const tableName = currentTable;

            deleteRow(tableName, rowId);
        }
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

function editRow(table, id, updatedData) {
    const url = `/database/${table}/${id}`;

    fetch(url, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(updatedData),
    })
        .then(response => {
            if (!response.ok) throw new Error(`Ошибка при редактировании записи в таблице ${table}`);
            loadData(currentTable, currentPage, currentSize, currentSearchQuery); // Обновляем данные таблицы
        })
        .catch(error => console.error('Ошибка редактирования:', error));
}

function deleteRow(table, id) {
    if (confirm("Вы уверены, что хотите удалить запись?")) {
        fetch(`/database/${table}/${id}`, {
            method: 'DELETE',
        })
            .then(response => {
                if (!response.ok) throw new Error(`Ошибка при удалении записи из таблицы ${table}`);
                loadData(currentTable, currentPage, currentSize, currentSearchQuery); // Обновляем таблицу
            })
            .catch(error => console.error('Ошибка удаления:', error));
    }
}


function generateClientRowsHtml(page) {
    const rows = page.map(client => `
        <tr>
            <td>${client.firstName}</td>
            <td>${client.lastName}</td>
            <td>${client.phone || ''}</td>
            <td>
                <button class="btn btn-warning btn-sm" onclick="editRow('clients', ${client.id}, { /* данные */ })">Edit</button>
                <button class="btn btn-danger btn-sm" onclick="deleteRow('clients', ${client.id})">Delete</button>
            </td>
        </tr>
    `).join('');
    return rows;
}


// Функция изменения страницы
function changePage(page) {
    currentPage = page;
    loadData(currentTable, currentPage, currentSize, currentSearchQuery);
}


