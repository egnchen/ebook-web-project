# Ebook

Ebook website - web development course final assignment @ sophomore B.

## Architecture

This project features a front-rear separated architecture, being composed of a frontend implemented in `VueJS` and backend implemented with powerful `Spring` framework.

## Installation & running

1. Clone the project(needless to say)
2. `cd` into `frontend` directory and build the frontend

    ```
    cd frontend
    yarn install
    yarn build
    ```
    All built frontend file will be gracefully handled and put into `/src/main/resources/static` directory, so that Tomcat can serve them as static contents.

3. Seed the database(optional)

    Currently the database is empty and I haven't synced any `sql` dump file into this repo. A `Douban Book` Crawler is implemented and placed in `spider` directory. You can see what you can do.

4. Run the application

    Follow these steps:
    * Open the project in IntellIJ IDEA Ultimate
    * Run the spring application
    * Visit `http://localhost:8080`