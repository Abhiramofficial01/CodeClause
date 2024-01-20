// script.js
$(document).ready(() => {
    // Fetch and display books on page load
    fetchBooks();

    // Add event listener for form submission
    $('#addBookForm').submit((event) => {
        event.preventDefault();
        addBook();
    });
});

function fetchBooks() {
    // Fetch books from the server and display on the page
    $.get('/api/books', (data) => {
        const bookList = $('#bookList');
        bookList.empty();

        data.forEach((book) => {
            bookList.append(`<p>${book.title} by ${book.author}</p>`);
        });
    });
}

function addBook() {
    // Get values from the form
    const title = $('#title').val();
    const author = $('#author').val();

    // Make a POST request to add a new book
    $.post('/api/books', { title, author }, (data) => {
        // Clear the form
        $('#title').val('');
        $('#author').val('');

        // Fetch and display updated book list
        fetchBooks();
    });
}
