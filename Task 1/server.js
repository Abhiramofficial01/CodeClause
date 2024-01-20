// server.js
const express = require('express');
const mongoose = require('mongoose');
const bodyParser = require('body-parser');

const app = express();
const PORT = 3000;

mongoose.connect('mongodb://localhost/library', { useNewUrlParser: true, useUnifiedTopology: true });
mongoose.set('useFindAndModify', false);

const bookSchema = new mongoose.Schema({
    title: String,
    author: String,
});

const Book = mongoose.model('Book', bookSchema);

app.use(bodyParser.json());

// API to get all books
app.get('/api/books', async (req, res) => {
    try {
        const books = await Book.find();
        res.json(books);
    } catch (error) {
        res.status(500).send(error.message);
    }
});

// API to add a new book
app.post('/api/books', async (req, res) => {
    const { title, author } = req.body;
    const newBook = new Book({ title, author });

    try {
        await newBook.save();
        res.json(newBook);
    } catch (error) {
        res.status(500).send(error.message);
    }
});

app.listen(PORT, () => {
    console.log(`Server is running on port ${PORT}`);
});
