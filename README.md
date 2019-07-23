# captcha_solver
service made in Java to solve captcha's using a middleman -> in this case a MySQL database

![alt text](https://i.gyazo.com/a9f4eab8b9cbcdfb68a56df8697ab735.png)

uses a MySQL database as middleman, reads the database where the captcha_key is still null, solves it with captcha provider, then updates that row with the solved captcha_key value.

is multi-threaded.
