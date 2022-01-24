# School Registration API

### Reference Documentation

This API allows course and students management. The features are:

* Rol up new users. You can create two types of users. Administrator and Students.
* Manage Students. You can create/update/delete and query students.
* Manage Courses. Your can create/update/delete and query courses.
* Enroll/Unroll Students. You can add up to 5 courses to each student and unroll from any previous course.

They are two roles available in this API:
1. **USER:** default type when students are created and allow enroll/unroll operations.
2. **ADMIN:** Users rights plus CRUD over courses/students.
### API endpoints

The following list depict the available endpoints of the API:

1. Authentication

    * [User Sign Up (POST)](http://localhost:8082/api/v1/auth/users) : *Allow to create a new user in the system.*

      Request:

        ```
         curl --location --request POST 'http://localhost:8082/api/v1/auth/users' \
              --header 'Content-Type: application/json' \
              --header 'Accept: application/json' \
              --header 'If-None-Match: 098e97de3b61db55286f5f2812785116f' \
              --data-raw '{
                             "username": "scott2",
                             "password": "tiger",
                             "firstName": "Bruce",
                             "lastName": "Scott2",
                             "email": "bruce2@scott.db",
                             "role": "ROLE_ADMIN"
                           }'
        ```
      Response:

        ```
          {
              "links": [
                  {
                     "rel": "user-signin",
                     "href": "http://localhost:8082/api/v1/auth/token"
                  }
              ],
              "id": "443fc613-430e-42eb-8ba8-6709a803cb45",
              "username": "scott2",
              "firstName": "Bruce",
              "lastName": "Scott2",
              "email": "bruce2@scott.db",
              "password": "Ciphered...",
              "role": "ROLE_ADMIN"
          }
        ```
    * [User Sign In (POST)](http://localhost:8082/api/v1/auth/token): *Allow user Authentication.*

      Request:

           curl --location --request POST 'http://localhost:8082/api/v1/auth/token' \
                 --header 'Content-Type: application/json' \
                 --header 'Accept: application/json' \
                 --header 'If-None-Match: "0a54ae579d2f1e6a7050d6775e602a9ab' \
                 --data-raw '{
                                "username": "scott2",
                                "password": "tiger"
                             }'

      Response:

            {
              "links": [
                         {
                            "rel": "user-team",
                            "href": "http://localhost:8082/api/v1/team/443fc613-430e-42eb-8ba8-6709a803cb45"
                         }
                       ],
              "refreshToken": "m92k6sl4cdm3lv08adu9suce1lhp47l73cue518e6a3m0pb12ool6gp7dei0bpepdr4bncpsbtcoftduldjd7fdnq1brg1t5a7dqqatvo24tratmn9favv09ibur226d",
              "accessToken": "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJzY290dDIiLCJyb2xlcyI6WyJBRE1JTiJdLCJpc3MiOiJEcmVhbSBUZWFtIEFQSSIsImV4cCI6MTYzOTc3ODM4NywiaWF0IjoxNjM5Nzc3NDg3fQ.G-bpC59K4nPvZ7xYxTJ67JCUm41IZUE70AviRtk2RUBb2E4X5y0qjr08LPTJpkl-5c7CqkBC7XkyEtLPITYfFd-vs0AIB1JkfuLIykvte1QZ6jTjfdgteqX7QXOJfd0n5xjoXcWT0Z_oR84t9OSSLpneNpROKrOvROyxf6mrNK7M8FnNnzTDqk-fDHBbNrCZKG1GX2PawGocnOUw_qPd2Y08MVoaYzHj61MAKsqTn_9rEViY0T9mRLCgTaekHewN9dAdIGsg7UR0s78nY7Hnd8d3bcPeLLeR1o3tZwyXCqBML0hPQ9NyvmuPmepKbgtEmJOMNhLKT4Fk9yaU5IXnlb7WWkmboFsIm-gIOWfYSgW2Qv1RjVwx-aCZ5sxFACcIrqIBVgLiYMrulzKzVxZFyMSLDW3SrJKuT8DYABqiY6kuoGopx_vjL_3J-bEdhoZrTEJ6JweEIt1LQiYD0deQpL61EsolkYriJgKpxn5VNhFexn6IacHfLZP11jc0e95Wu-Rlc1cbt4tCUMepY0SibHHkHL6B-5AzCeQrWZkIxh1HIhO23bZ72_lZDHzeteHotXFqCaXUGqo5wS4M7uBNpUgp1JL8IW68SmbTXlnhWeyQI3Ybdh_f3QDqZGyizMm3AIy42tPRgBHsA3Swpcrlb50DTg8QEajlW18MbAUFs28",
              "username": "scott2",
              "userId": "443fc613-430e-42eb-8ba8-6709a803cb45"
      }

    * [User Refresh Tokens (POST)](http://localhost:8082/api/v1/auth/token/refresh): *Allow refresh the JWT access token.*

      Request:

            curl --location --request POST 'http://localhost:8082/api/v1/auth/token/refresh' \
                 --header 'Content-Type: application/json' \
                 --header 'Accept: application/json' \
                 --header 'If-None-Match: "0a54ae579d2f1e6a7050d6775e602a9ab' \
                 --data-raw '{
                                "refreshToken": "m92k6sl4cdm3lv08adu9suce1lhp47l73cue518e6a3m0pb12ool6gp7dei0bpepdr4bncpsbtcoftduldjd7fdnq1brg1t5a7dqqatvo24tratmn9favv09ibur226d"
                            }'

      Response:

            {
              "links": [],
              "refreshToken": "m92k6sl4cdm3lv08adu9suce1lhp47l73cue518e6a3m0pb12ool6gp7dei0bpepdr4bncpsbtcoftduldjd7fdnq1brg1t5a7dqqatvo24tratmn9favv09ibur226d",
              "accessToken": "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJzY290dDIiLCJyb2xlcyI6WyJBRE1JTiJdLCJpc3MiOiJEcmVhbSBUZWFtIEFQSSIsImV4cCI6MTYzOTc3ODY4NywiaWF0IjoxNjM5Nzc3Nzg3fQ.Jud34UpYN1d1L_NSVGvgmLraXmiGf5uTjQI5tIUp9PjeP9UEc70SQcy-WlDX1-hO0P8bxyfuOy48eH0D169CIIEGs4I87qgR8HugQFvzuw4vaZiiDH4JX-By-BuSdm6-NDg5XjS3EHBtGnE3IID7qgAA0DHaPp-cZZ82bsyCGbDm8OhwA6uMPwZSG-oariWULtBGds44iTpXivKC8dutvwVvoKcfVP1FTdB9YHwfULabqZ-PmYkoI8cI_Jd6p97VdzBfin2bg-56s0UxbPeGs5LcYnFgNp-gWOYlNqaeF9QMlsKinnm0wbhWRWgu6EAW-UdwcKeLGN4r9skZ01BtneQaeDNWOGak9119gPU5xHlx8Vu1Cz1VCGzyMdvmcU4SKAq7JYCthUBkTeXSGPO3IzRdgGb_86o3XptObZvDssbeSV-cAhjjz9UiuIVbrkeEZD0frd6GfyRig3NMUdlTQOu9Uj0Pyh85a1cqX_nh-dKV0EFOyXrhIlAr9AHDwAyx6lqfD3tl8bjzgID5bSUsA0ZTmZxWgpiz4BnLF3otBGkHe62hCHZltD8_3G8GxRkC88mwYgPGehlcZH91LZs6lfwTLqn0gejiaAdcJTde5FFVXvJkMd6H07ELwW01w27XyRGRB4wUTdu_2uCzC27rUltu0PYYk3MJs-J_eDAEjpY",
              "username": "scott2",
              "userId": "443fc613-430e-42eb-8ba8-6709a803cb45"
            }

    * [User Sign Out (DELETE)](http://localhost:8082/api/v1/auth/token): *Allow ending the user session.*

      Request:

            curl --location --request DELETE 'http://localhost:8082/api/v1/auth/token' \
                 --header 'Content-Type: application/json' \
                 --header 'Accept: application/json' \
                 --header 'If-None-Match: "0a54ae579d2f1e6a7050d6775e602a9ab' \
                 --data-raw '{
                                 "refreshToken": "m92k6sl4cdm3lv08adu9suce1lhp47l73cue518e6a3m0pb12ool6gp7dei0bpepdr4bncpsbtcoftduldjd7fdnq1brg1t5a7dqqatvo24tratmn9favv09ibur226d"
                            }'

      Response:
        ```
           No content
        ```
2. Course
    * [Create Course (POST)/**ADMIN**](http://localhost:8082/api/v1/course/): *Create new course.*

      Request:

        ```
         curl --location --request POST 'http://localhost:8082/api/v1/course/' \
              --header 'Content-Type: application/json' \
              --header 'Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJzY290dDIiLCJyb2xlcyI6WyJBRE1JTiJdLCJpc3MiOiJTY2hvb2wgUmVnaXN0cmF0aW9uIEFQSSIsImV4cCI6MTY0MzkwMzQ3OSwiaWF0IjoxNjQyOTAzNDc5fQ.ObwDWp2Y2tiXWS8yfJqM7ifyA1xvsyKXnlf-zIjmpVsubw1F8ZUm2cOu-Axi0OrcjkIhN6zib8EWvhEErR5Fhb1X6Uhz6NGrAvNDO_XtsoSfZC6O5QzGS0BFIAflaJr6SZDzajCU5mlP9GQulonDdisu9Qh18eP2fj8D4r0Wqy_a-pSZLqsLlxWy2id2IUv9_wphxUIxcyLltIQwzbjmMpY1H6Tjg6YxZOUBvWwfg9eR1oFwOqFcACpwZXM9uxK5KiV-pvCZVze6ISgJa_hFmlueCBsk_iPRKy8nMb27A0U_w20wd4h-4fsMGHXuoLwHnTQRNy3dEjzKa0u7r07isiAJ3fYU1hB-kuk-Prg31LC0tu3QJufwt8jRPm4HNcHnKzs786MsMx-lVWw1xnIuHi-64SrRSdlJ4jDCX-UC8TkYmE3tQNA-dui76LjvUKp_6hPnVUsG8sMdFEkDwCYKNPfYZctqVGefOq6GZvWN8cocZybjsDcTnMMdJq9DIGaEgsaaT6SzKx8i1W1bWYRgojPzR-_U_D60Xm4Rz231IOGWd38-_udQLTcSCldJ7c6Am-gAJ7w4ujrAJ478j_xZiqQcg7BFNtw_0isYgY5klyVjQy_uz3giP0uOio4LFd8c8J3YpxwR6Z79H9DdKibnkdD5jitOCgwKHmbCQWqtSQA' \
              --data-raw '{
                            "name": "math3"
              }'
        ```
      Response:

        ```
          {
            "id": "7d51803a-7e30-495f-8cad-44eedb8bc43b",
            "name": "math3",
            "students": [],
            "links": [
               {
                  "rel": "self",
                  "href": "http://localhost:8082/api/v1/course/7d51803a-7e30-495f-8cad-44eedb8bc43b/7d51803a-7e30-495f-8cad-44eedb8bc43b"
               }
              ]
          }
        ```
    * [Update Course (PUT)/**ADMIN**](http://localhost:8082/api/v1/course): *Update existing course.*

      Request:

        ```
         curl --location --request PUT 'http://localhost:8082/api/v1/course/' \
              --header 'Content-Type: application/json' \
              --header 'Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJzY290dDIiLCJyb2xlcyI6WyJBRE1JTiJdLCJpc3MiOiJTY2hvb2wgUmVnaXN0cmF0aW9uIEFQSSIsImV4cCI6MTY0MzkwMzQ3OSwiaWF0IjoxNjQyOTAzNDc5fQ.ObwDWp2Y2tiXWS8yfJqM7ifyA1xvsyKXnlf-zIjmpVsubw1F8ZUm2cOu-Axi0OrcjkIhN6zib8EWvhEErR5Fhb1X6Uhz6NGrAvNDO_XtsoSfZC6O5QzGS0BFIAflaJr6SZDzajCU5mlP9GQulonDdisu9Qh18eP2fj8D4r0Wqy_a-pSZLqsLlxWy2id2IUv9_wphxUIxcyLltIQwzbjmMpY1H6Tjg6YxZOUBvWwfg9eR1oFwOqFcACpwZXM9uxK5KiV-pvCZVze6ISgJa_hFmlueCBsk_iPRKy8nMb27A0U_w20wd4h-4fsMGHXuoLwHnTQRNy3dEjzKa0u7r07isiAJ3fYU1hB-kuk-Prg31LC0tu3QJufwt8jRPm4HNcHnKzs786MsMx-lVWw1xnIuHi-64SrRSdlJ4jDCX-UC8TkYmE3tQNA-dui76LjvUKp_6hPnVUsG8sMdFEkDwCYKNPfYZctqVGefOq6GZvWN8cocZybjsDcTnMMdJq9DIGaEgsaaT6SzKx8i1W1bWYRgojPzR-_U_D60Xm4Rz231IOGWd38-_udQLTcSCldJ7c6Am-gAJ7w4ujrAJ478j_xZiqQcg7BFNtw_0isYgY5klyVjQy_uz3giP0uOio4LFd8c8J3YpxwR6Z79H9DdKibnkdD5jitOCgwKHmbCQWqtSQA' \
              --data-raw '{
                     "id": "7d51803a-7e30-495f-8cad-44eedb8bc43b",
                     "name": "advance math"
              }'
        ```
      Response:

        ```
          {
            "id": "7d51803a-7e30-495f-8cad-44eedb8bc43b",
            "name": "advance math",
            "students": [],
            "links": [
               {
                 "rel": "self",
                 "href": "http://localhost:8082/api/v1/course/7d51803a-7e30-495f-8cad-44eedb8bc43b/7d51803a-7e30-495f-8cad-44eedb8bc43b"
               }
            ]
          }
        ```
    * [Delete Course (DELETE)/**ADMIN**](http://localhost:8082/api/v1/course/{{courseId}}): *Delete an existing course.*

      Request:

        ```
         curl --location --request DELETE 'http://localhost:8082/api/v1/course/7d51803a-7e30-495f-8cad-44eedb8bc43b' \
              --header 'Content-Type: application/json' \
              --header 'Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJzY290dDIiLCJyb2xlcyI6WyJBRE1JTiJdLCJpc3MiOiJTY2hvb2wgUmVnaXN0cmF0aW9uIEFQSSIsImV4cCI6MTY0MzkwMzQ3OSwiaWF0IjoxNjQyOTAzNDc5fQ.ObwDWp2Y2tiXWS8yfJqM7ifyA1xvsyKXnlf-zIjmpVsubw1F8ZUm2cOu-Axi0OrcjkIhN6zib8EWvhEErR5Fhb1X6Uhz6NGrAvNDO_XtsoSfZC6O5QzGS0BFIAflaJr6SZDzajCU5mlP9GQulonDdisu9Qh18eP2fj8D4r0Wqy_a-pSZLqsLlxWy2id2IUv9_wphxUIxcyLltIQwzbjmMpY1H6Tjg6YxZOUBvWwfg9eR1oFwOqFcACpwZXM9uxK5KiV-pvCZVze6ISgJa_hFmlueCBsk_iPRKy8nMb27A0U_w20wd4h-4fsMGHXuoLwHnTQRNy3dEjzKa0u7r07isiAJ3fYU1hB-kuk-Prg31LC0tu3QJufwt8jRPm4HNcHnKzs786MsMx-lVWw1xnIuHi-64SrRSdlJ4jDCX-UC8TkYmE3tQNA-dui76LjvUKp_6hPnVUsG8sMdFEkDwCYKNPfYZctqVGefOq6GZvWN8cocZybjsDcTnMMdJq9DIGaEgsaaT6SzKx8i1W1bWYRgojPzR-_U_D60Xm4Rz231IOGWd38-_udQLTcSCldJ7c6Am-gAJ7w4ujrAJ478j_xZiqQcg7BFNtw_0isYgY5klyVjQy_uz3giP0uOio4LFd8c8J3YpxwR6Z79H9DdKibnkdD5jitOCgwKHmbCQWqtSQA'
        ```
      Response:

        ```
          {
            "id": "7d51803a-7e30-495f-8cad-44eedb8bc43b",
            "name": "advance math",
            "students": [],
            "links": [
              {
                 "rel": "self",
                 "href": "http://localhost:8082/api/v1/course/7d51803a-7e30-495f-8cad-44eedb8bc43b/7d51803a-7e30-495f-8cad-44eedb8bc43b"
              }
             ]
          }
        ```
    * [Find Course (GET)](http://localhost:8082/api/v1/course/{{courseId}}): *Search a course by Id.*

      Request:

        ```
         curl --location --request GET 'http://localhost:8082/api/v1/course/ab0bdfa7-75a4-4d62-95b6-506a21186db4' \
              --header 'Content-Type: application/json' \
              --header 'Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJzY290dDIiLCJyb2xlcyI6WyJBRE1JTiJdLCJpc3MiOiJTY2hvb2wgUmVnaXN0cmF0aW9uIEFQSSIsImV4cCI6MTY0MzkwMzQ3OSwiaWF0IjoxNjQyOTAzNDc5fQ.ObwDWp2Y2tiXWS8yfJqM7ifyA1xvsyKXnlf-zIjmpVsubw1F8ZUm2cOu-Axi0OrcjkIhN6zib8EWvhEErR5Fhb1X6Uhz6NGrAvNDO_XtsoSfZC6O5QzGS0BFIAflaJr6SZDzajCU5mlP9GQulonDdisu9Qh18eP2fj8D4r0Wqy_a-pSZLqsLlxWy2id2IUv9_wphxUIxcyLltIQwzbjmMpY1H6Tjg6YxZOUBvWwfg9eR1oFwOqFcACpwZXM9uxK5KiV-pvCZVze6ISgJa_hFmlueCBsk_iPRKy8nMb27A0U_w20wd4h-4fsMGHXuoLwHnTQRNy3dEjzKa0u7r07isiAJ3fYU1hB-kuk-Prg31LC0tu3QJufwt8jRPm4HNcHnKzs786MsMx-lVWw1xnIuHi-64SrRSdlJ4jDCX-UC8TkYmE3tQNA-dui76LjvUKp_6hPnVUsG8sMdFEkDwCYKNPfYZctqVGefOq6GZvWN8cocZybjsDcTnMMdJq9DIGaEgsaaT6SzKx8i1W1bWYRgojPzR-_U_D60Xm4Rz231IOGWd38-_udQLTcSCldJ7c6Am-gAJ7w4ujrAJ478j_xZiqQcg7BFNtw_0isYgY5klyVjQy_uz3giP0uOio4LFd8c8J3YpxwR6Z79H9DdKibnkdD5jitOCgwKHmbCQWqtSQA'
        ```
      Response:

        ```
          {
            "id": "ab0bdfa7-75a4-4d62-95b6-506a21186db4",
            "name": "math3",
            "students": [],
            "links": [
               {
                  "rel": "self",
                  "href": "http://localhost:8082/api/v1/course/ab0bdfa7-75a4-4d62-95b6-506a21186db4/ab0bdfa7-75a4-4d62-95b6-506a21186db4"
              }
           ]
         }
        ```
        * [Find All Courses (GET)](http://localhost:8082/api/v1/course/all?page=3&size=2): *Retrieve all the existing courses.*

          Request:

            ```
             curl --location --request GET 'http://localhost:8082/api/v1/course/all?page=3&size=2' \
                  --header 'Content-Type: application/json' \
                  --header 'Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJzY290dDIiLCJyb2xlcyI6WyJBRE1JTiJdLCJpc3MiOiJTY2hvb2wgUmVnaXN0cmF0aW9uIEFQSSIsImV4cCI6MTY0MzkwMzQ3OSwiaWF0IjoxNjQyOTAzNDc5fQ.ObwDWp2Y2tiXWS8yfJqM7ifyA1xvsyKXnlf-zIjmpVsubw1F8ZUm2cOu-Axi0OrcjkIhN6zib8EWvhEErR5Fhb1X6Uhz6NGrAvNDO_XtsoSfZC6O5QzGS0BFIAflaJr6SZDzajCU5mlP9GQulonDdisu9Qh18eP2fj8D4r0Wqy_a-pSZLqsLlxWy2id2IUv9_wphxUIxcyLltIQwzbjmMpY1H6Tjg6YxZOUBvWwfg9eR1oFwOqFcACpwZXM9uxK5KiV-pvCZVze6ISgJa_hFmlueCBsk_iPRKy8nMb27A0U_w20wd4h-4fsMGHXuoLwHnTQRNy3dEjzKa0u7r07isiAJ3fYU1hB-kuk-Prg31LC0tu3QJufwt8jRPm4HNcHnKzs786MsMx-lVWw1xnIuHi-64SrRSdlJ4jDCX-UC8TkYmE3tQNA-dui76LjvUKp_6hPnVUsG8sMdFEkDwCYKNPfYZctqVGefOq6GZvWN8cocZybjsDcTnMMdJq9DIGaEgsaaT6SzKx8i1W1bWYRgojPzR-_U_D60Xm4Rz231IOGWd38-_udQLTcSCldJ7c6Am-gAJ7w4ujrAJ478j_xZiqQcg7BFNtw_0isYgY5klyVjQy_uz3giP0uOio4LFd8c8J3YpxwR6Z79H9DdKibnkdD5jitOCgwKHmbCQWqtSQA'
            ```
          Response:

            ```
              {
                "links": [
                   {
                      "rel": "first",
                      "href": "http://localhost:8082/api/v1/course/all?page=0&size=2"
                   },
                  {
                      "rel": "prev",
                      "href": "http://localhost:8082/api/v1/course/all?page=2&size=2"
                  },
                 {
                      "rel": "self",
                      "href": "http://localhost:8082/api/v1/course/all?page=3&size=2"
                 },
                {
                      "rel": "last",
                      "href": "http://localhost:8082/api/v1/course/all?page=0&size=2"
                }
               ],
             "content": [],
             "page": {
                       "size": 2,
                       "totalElements": 1,
                       "totalPages": 1,
                       "number": 3
              }
            }
            ```
      * [Find Student's Courses (GET)](http://localhost:8082/api/v1/course/student/{{studentId}}): *Retrieve all the courses the student was enrolled in.*

      Request:

        ```
         curl --location --request GET 'http://localhost:8082/api/v1/course/student/fec5b531-ac92-40fd-9ba6-4eb2d865e257' \
              --header 'Content-Type: application/json' \
              --header 'Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJzY290dDIiLCJyb2xlcyI6WyJBRE1JTiJdLCJpc3MiOiJTY2hvb2wgUmVnaXN0cmF0aW9uIEFQSSIsImV4cCI6MTY0MzkwMzQ3OSwiaWF0IjoxNjQyOTAzNDc5fQ.ObwDWp2Y2tiXWS8yfJqM7ifyA1xvsyKXnlf-zIjmpVsubw1F8ZUm2cOu-Axi0OrcjkIhN6zib8EWvhEErR5Fhb1X6Uhz6NGrAvNDO_XtsoSfZC6O5QzGS0BFIAflaJr6SZDzajCU5mlP9GQulonDdisu9Qh18eP2fj8D4r0Wqy_a-pSZLqsLlxWy2id2IUv9_wphxUIxcyLltIQwzbjmMpY1H6Tjg6YxZOUBvWwfg9eR1oFwOqFcACpwZXM9uxK5KiV-pvCZVze6ISgJa_hFmlueCBsk_iPRKy8nMb27A0U_w20wd4h-4fsMGHXuoLwHnTQRNy3dEjzKa0u7r07isiAJ3fYU1hB-kuk-Prg31LC0tu3QJufwt8jRPm4HNcHnKzs786MsMx-lVWw1xnIuHi-64SrRSdlJ4jDCX-UC8TkYmE3tQNA-dui76LjvUKp_6hPnVUsG8sMdFEkDwCYKNPfYZctqVGefOq6GZvWN8cocZybjsDcTnMMdJq9DIGaEgsaaT6SzKx8i1W1bWYRgojPzR-_U_D60Xm4Rz231IOGWd38-_udQLTcSCldJ7c6Am-gAJ7w4ujrAJ478j_xZiqQcg7BFNtw_0isYgY5klyVjQy_uz3giP0uOio4LFd8c8J3YpxwR6Z79H9DdKibnkdD5jitOCgwKHmbCQWqtSQA'
        ```
      Response:

        ```
          {
             "links": [ 
               {
                  "rel": "self",
                  "href": "http://localhost:8082/api/v1/course/student/fec5b531-ac92-40fd-9ba6-4eb2d865e257?page=0&size=20"
               }
              ],
             "content": [],
             "page": {
                       "size": 20,
                       "totalElements": 0,
                       "totalPages": 0,
                       "number": 0
                     }
              }
        ```         
      * [Find Courses without Students (GET)/**ADMIN**](http://localhost:8082/api/v1/course/empty): *Retrieve all the courses the doesn't have student enrolled.*

      Request:

        ```
         curl --location --request GET 'http://localhost:8082/api/v1/course/empty' \
              --header 'Content-Type: application/json' \
              --header 'Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJzY290dDIiLCJyb2xlcyI6WyJBRE1JTiJdLCJpc3MiOiJTY2hvb2wgUmVnaXN0cmF0aW9uIEFQSSIsImV4cCI6MTY0MzkwMzQ3OSwiaWF0IjoxNjQyOTAzNDc5fQ.ObwDWp2Y2tiXWS8yfJqM7ifyA1xvsyKXnlf-zIjmpVsubw1F8ZUm2cOu-Axi0OrcjkIhN6zib8EWvhEErR5Fhb1X6Uhz6NGrAvNDO_XtsoSfZC6O5QzGS0BFIAflaJr6SZDzajCU5mlP9GQulonDdisu9Qh18eP2fj8D4r0Wqy_a-pSZLqsLlxWy2id2IUv9_wphxUIxcyLltIQwzbjmMpY1H6Tjg6YxZOUBvWwfg9eR1oFwOqFcACpwZXM9uxK5KiV-pvCZVze6ISgJa_hFmlueCBsk_iPRKy8nMb27A0U_w20wd4h-4fsMGHXuoLwHnTQRNy3dEjzKa0u7r07isiAJ3fYU1hB-kuk-Prg31LC0tu3QJufwt8jRPm4HNcHnKzs786MsMx-lVWw1xnIuHi-64SrRSdlJ4jDCX-UC8TkYmE3tQNA-dui76LjvUKp_6hPnVUsG8sMdFEkDwCYKNPfYZctqVGefOq6GZvWN8cocZybjsDcTnMMdJq9DIGaEgsaaT6SzKx8i1W1bWYRgojPzR-_U_D60Xm4Rz231IOGWd38-_udQLTcSCldJ7c6Am-gAJ7w4ujrAJ478j_xZiqQcg7BFNtw_0isYgY5klyVjQy_uz3giP0uOio4LFd8c8J3YpxwR6Z79H9DdKibnkdD5jitOCgwKHmbCQWqtSQA'
        ```
      Response:

        ```
          {
             "links": [
                {
                   "rel": "self",
                   "href": "http://localhost:8082/api/v1/course/empty?page=0&size=20"
                }
             ],
             "content": [
                {
                   "id": "ab0bdfa7-75a4-4d62-95b6-506a21186db4",
                   "name": "math3",
                   "students": [],
                   "links": [
                      {
                         "rel": "self",
                         "href": "http://localhost:8082/api/v1/course/ab0bdfa7-75a4-4d62-95b6-506a21186db4/ab0bdfa7-75a4-4d62-95b6-506a21186db4"
                      }
                  ]
                }
               ],
             "page": {
                "size": 20,
                "totalElements": 1,
                "totalPages": 1,
                "number": 0
             }
          }
        ```
3. Students
   * [Create Student (POST)/**ADMIN**](http://localhost:8082/api/v1/student/): *Create new student.*

     Request:

       ```
        curl --location --request POST 'http://localhost:8082/api/v1/student/' \
             --header 'Content-Type: application/json' \
             --header 'Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJzY290dDIiLCJyb2xlcyI6WyJBRE1JTiJdLCJpc3MiOiJTY2hvb2wgUmVnaXN0cmF0aW9uIEFQSSIsImV4cCI6MTY0MzkwMzQ3OSwiaWF0IjoxNjQyOTAzNDc5fQ.ObwDWp2Y2tiXWS8yfJqM7ifyA1xvsyKXnlf-zIjmpVsubw1F8ZUm2cOu-Axi0OrcjkIhN6zib8EWvhEErR5Fhb1X6Uhz6NGrAvNDO_XtsoSfZC6O5QzGS0BFIAflaJr6SZDzajCU5mlP9GQulonDdisu9Qh18eP2fj8D4r0Wqy_a-pSZLqsLlxWy2id2IUv9_wphxUIxcyLltIQwzbjmMpY1H6Tjg6YxZOUBvWwfg9eR1oFwOqFcACpwZXM9uxK5KiV-pvCZVze6ISgJa_hFmlueCBsk_iPRKy8nMb27A0U_w20wd4h-4fsMGHXuoLwHnTQRNy3dEjzKa0u7r07isiAJ3fYU1hB-kuk-Prg31LC0tu3QJufwt8jRPm4HNcHnKzs786MsMx-lVWw1xnIuHi-64SrRSdlJ4jDCX-UC8TkYmE3tQNA-dui76LjvUKp_6hPnVUsG8sMdFEkDwCYKNPfYZctqVGefOq6GZvWN8cocZybjsDcTnMMdJq9DIGaEgsaaT6SzKx8i1W1bWYRgojPzR-_U_D60Xm4Rz231IOGWd38-_udQLTcSCldJ7c6Am-gAJ7w4ujrAJ478j_xZiqQcg7BFNtw_0isYgY5klyVjQy_uz3giP0uOio4LFd8c8J3YpxwR6Z79H9DdKibnkdD5jitOCgwKHmbCQWqtSQA' \
             --data-raw '{
                  "username": "jose.mora",
                  "firstName": "Jose",
                  "lastName": "Mora",
                  "email": "josealejonino@gmail.com"
               }'
       ```
     Response:

       ```
         {
            "id": "f20aa5ff-683e-4ce6-baf6-35a3ebb52e81",
            "user": {
                      "id": "1defafa1-fc85-4d1b-a7be-75769f6bd85d",
                      "username": "jose.mora",
                      "firstName": "Jose",
                      "lastName": "Mora",
                      "email": "josealejonino@gmail.com",
                      "password": "Ciphered...",
                      "role": "ROLE_USER",
                      "links": [
                         {
                           "rel": "user-signin",
                           "href": "http://localhost:8082/api/v1/auth/token"
                         }
                       ]
                    },
            "courses": [],
            "links": [
               {
                  "rel": "self",
                  "href": "http://localhost:8082/api/v1/student/f20aa5ff-683e-4ce6-baf6-35a3ebb52e81/f20aa5ff-683e-4ce6-baf6-35a3ebb52e81"
               }
             ]
         }
       ```
   * [Update Student (PUT)/**ADMIN**](http://localhost:8082/api/v1/student/): *Update an existing student.*

     Request:

       ```
        curl --location --request PUT 'http://localhost:8082/api/v1/student/' \
             --header 'Content-Type: application/json' \
             --header 'Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJzY290dDIiLCJyb2xlcyI6WyJBRE1JTiJdLCJpc3MiOiJTY2hvb2wgUmVnaXN0cmF0aW9uIEFQSSIsImV4cCI6MTY0MzkwMzQ3OSwiaWF0IjoxNjQyOTAzNDc5fQ.ObwDWp2Y2tiXWS8yfJqM7ifyA1xvsyKXnlf-zIjmpVsubw1F8ZUm2cOu-Axi0OrcjkIhN6zib8EWvhEErR5Fhb1X6Uhz6NGrAvNDO_XtsoSfZC6O5QzGS0BFIAflaJr6SZDzajCU5mlP9GQulonDdisu9Qh18eP2fj8D4r0Wqy_a-pSZLqsLlxWy2id2IUv9_wphxUIxcyLltIQwzbjmMpY1H6Tjg6YxZOUBvWwfg9eR1oFwOqFcACpwZXM9uxK5KiV-pvCZVze6ISgJa_hFmlueCBsk_iPRKy8nMb27A0U_w20wd4h-4fsMGHXuoLwHnTQRNy3dEjzKa0u7r07isiAJ3fYU1hB-kuk-Prg31LC0tu3QJufwt8jRPm4HNcHnKzs786MsMx-lVWw1xnIuHi-64SrRSdlJ4jDCX-UC8TkYmE3tQNA-dui76LjvUKp_6hPnVUsG8sMdFEkDwCYKNPfYZctqVGefOq6GZvWN8cocZybjsDcTnMMdJq9DIGaEgsaaT6SzKx8i1W1bWYRgojPzR-_U_D60Xm4Rz231IOGWd38-_udQLTcSCldJ7c6Am-gAJ7w4ujrAJ478j_xZiqQcg7BFNtw_0isYgY5klyVjQy_uz3giP0uOio4LFd8c8J3YpxwR6Z79H9DdKibnkdD5jitOCgwKHmbCQWqtSQA' \
             --data-raw '{
                   "id": "f20aa5ff-683e-4ce6-baf6-35a3ebb52e81",
                   "firstName": "Jose",
                   "lastName": "Niño",
                   "email": "josealejonino@gmail.com"
               }'
       ```
     Response:

       ```
         {
           "id": "f20aa5ff-683e-4ce6-baf6-35a3ebb52e81",
           "user": {
                     "id": "1defafa1-fc85-4d1b-a7be-75769f6bd85d",
                     "username": "jose.mora",
                     "firstName": "Jose",
                     "lastName": "Niño",
                     "email": "josealejonino@gmail.com",
                     "password": "Ciphered...",
                     "role": "ROLE_USER",
                     "links": [
                        {
                          "rel": "user-signin",
                          "href": "http://localhost:8082/api/v1/auth/token"
                        }
                     ]
                   },
          "courses": [],
          "links": [
             {
                "rel": "self",
                "href": "http://localhost:8082/api/v1/student/f20aa5ff-683e-4ce6-baf6-35a3ebb52e81/f20aa5ff-683e-4ce6-baf6-35a3ebb52e81"
             }
           ]
         }
       ```
   * [Delete Student (DELETE)/**ADMIN**](http://localhost:8082/api/v1/student/): *Delete an existing student.*

     Request:

       ```
        curl --location --request DELETE 'http://localhost:8082/api/v1/student/f20aa5ff-683e-4ce6-baf6-35a3ebb52e81' \
             --header 'Content-Type: application/json' \
             --header 'Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJzY290dDIiLCJyb2xlcyI6WyJBRE1JTiJdLCJpc3MiOiJTY2hvb2wgUmVnaXN0cmF0aW9uIEFQSSIsImV4cCI6MTY0MzkwMzQ3OSwiaWF0IjoxNjQyOTAzNDc5fQ.ObwDWp2Y2tiXWS8yfJqM7ifyA1xvsyKXnlf-zIjmpVsubw1F8ZUm2cOu-Axi0OrcjkIhN6zib8EWvhEErR5Fhb1X6Uhz6NGrAvNDO_XtsoSfZC6O5QzGS0BFIAflaJr6SZDzajCU5mlP9GQulonDdisu9Qh18eP2fj8D4r0Wqy_a-pSZLqsLlxWy2id2IUv9_wphxUIxcyLltIQwzbjmMpY1H6Tjg6YxZOUBvWwfg9eR1oFwOqFcACpwZXM9uxK5KiV-pvCZVze6ISgJa_hFmlueCBsk_iPRKy8nMb27A0U_w20wd4h-4fsMGHXuoLwHnTQRNy3dEjzKa0u7r07isiAJ3fYU1hB-kuk-Prg31LC0tu3QJufwt8jRPm4HNcHnKzs786MsMx-lVWw1xnIuHi-64SrRSdlJ4jDCX-UC8TkYmE3tQNA-dui76LjvUKp_6hPnVUsG8sMdFEkDwCYKNPfYZctqVGefOq6GZvWN8cocZybjsDcTnMMdJq9DIGaEgsaaT6SzKx8i1W1bWYRgojPzR-_U_D60Xm4Rz231IOGWd38-_udQLTcSCldJ7c6Am-gAJ7w4ujrAJ478j_xZiqQcg7BFNtw_0isYgY5klyVjQy_uz3giP0uOio4LFd8c8J3YpxwR6Z79H9DdKibnkdD5jitOCgwKHmbCQWqtSQA'
       ```
     Response:

       ```
         {
            "id": "f20aa5ff-683e-4ce6-baf6-35a3ebb52e81",
            "user": {
                      "id": "1defafa1-fc85-4d1b-a7be-75769f6bd85d",
                      "username": "jose.mora",
                      "firstName": "Jose",
                      "lastName": "Niño",
                      "email": "josealejonino@gmail.com",
                      "password": "Ciphered...",
                      "role": "ROLE_USER",
                      "links": [
                         {
                            "rel": "user-signin",
                            "href": "http://localhost:8082/api/v1/auth/token"
                         }
                       ]
                    },
            "courses": [],
            "links": [
               {
                  "rel": "self",
                  "href": "http://localhost:8082/api/v1/student/f20aa5ff-683e-4ce6-baf6-35a3ebb52e81/f20aa5ff-683e-4ce6-baf6-35a3ebb52e81"
               }
             ]
         }
       ```
   * [Find Student (GET)/**ADMIN**](http://localhost:8082/api/v1/student/{{studentId}}): *Find a student by Id.*

     Request:

       ```
        curl --location --request GET 'http://localhost:8082/api/v1/student/fbe6ef68-77e1-492e-832a-d3ce068ef2f0' \
             --header 'Content-Type: application/json' \
             --header 'Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJzY290dDIiLCJyb2xlcyI6WyJBRE1JTiJdLCJpc3MiOiJTY2hvb2wgUmVnaXN0cmF0aW9uIEFQSSIsImV4cCI6MTY0MzkwMzQ3OSwiaWF0IjoxNjQyOTAzNDc5fQ.ObwDWp2Y2tiXWS8yfJqM7ifyA1xvsyKXnlf-zIjmpVsubw1F8ZUm2cOu-Axi0OrcjkIhN6zib8EWvhEErR5Fhb1X6Uhz6NGrAvNDO_XtsoSfZC6O5QzGS0BFIAflaJr6SZDzajCU5mlP9GQulonDdisu9Qh18eP2fj8D4r0Wqy_a-pSZLqsLlxWy2id2IUv9_wphxUIxcyLltIQwzbjmMpY1H6Tjg6YxZOUBvWwfg9eR1oFwOqFcACpwZXM9uxK5KiV-pvCZVze6ISgJa_hFmlueCBsk_iPRKy8nMb27A0U_w20wd4h-4fsMGHXuoLwHnTQRNy3dEjzKa0u7r07isiAJ3fYU1hB-kuk-Prg31LC0tu3QJufwt8jRPm4HNcHnKzs786MsMx-lVWw1xnIuHi-64SrRSdlJ4jDCX-UC8TkYmE3tQNA-dui76LjvUKp_6hPnVUsG8sMdFEkDwCYKNPfYZctqVGefOq6GZvWN8cocZybjsDcTnMMdJq9DIGaEgsaaT6SzKx8i1W1bWYRgojPzR-_U_D60Xm4Rz231IOGWd38-_udQLTcSCldJ7c6Am-gAJ7w4ujrAJ478j_xZiqQcg7BFNtw_0isYgY5klyVjQy_uz3giP0uOio4LFd8c8J3YpxwR6Z79H9DdKibnkdD5jitOCgwKHmbCQWqtSQA'
       ```
     Response:

       ```
         {
            "id": "fbe6ef68-77e1-492e-832a-d3ce068ef2f0",
            "user": {
                       "id": "80916b7f-8c8b-40af-abee-d544c50812e7",
                       "username": "jose.mora",
                       "firstName": "Jose",
                       "lastName": "Mora",
                       "email": "josealejonino@gmail.com",
                       "password": "Ciphered...",
                       "role": "ROLE_USER",
                       "links": [
                          {
                             "rel": "user-signin",
                             "href": "http://localhost:8082/api/v1/auth/token"
                          }
                        ]
                    },
            "courses": [],
            "links": [
              {
                "rel": "self",
                "href": "http://localhost:8082/api/v1/student/fbe6ef68-77e1-492e-832a-d3ce068ef2f0/fbe6ef68-77e1-492e-832a-d3ce068ef2f0"
              }
             ]
         }
       ```
   * [Find All Students (GET)/**ADMIN**](http://localhost:8082/api/v1/student/all?page=0&size=2): *Return all the existing students.*

     Request:

       ```
        curl --location --request GET 'http://localhost:8082/api/v1/student/all?page=0&size=2' \
             --header 'Content-Type: application/json' \
             --header 'Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJzY290dDIiLCJyb2xlcyI6WyJBRE1JTiJdLCJpc3MiOiJTY2hvb2wgUmVnaXN0cmF0aW9uIEFQSSIsImV4cCI6MTY0MzkwMzQ3OSwiaWF0IjoxNjQyOTAzNDc5fQ.ObwDWp2Y2tiXWS8yfJqM7ifyA1xvsyKXnlf-zIjmpVsubw1F8ZUm2cOu-Axi0OrcjkIhN6zib8EWvhEErR5Fhb1X6Uhz6NGrAvNDO_XtsoSfZC6O5QzGS0BFIAflaJr6SZDzajCU5mlP9GQulonDdisu9Qh18eP2fj8D4r0Wqy_a-pSZLqsLlxWy2id2IUv9_wphxUIxcyLltIQwzbjmMpY1H6Tjg6YxZOUBvWwfg9eR1oFwOqFcACpwZXM9uxK5KiV-pvCZVze6ISgJa_hFmlueCBsk_iPRKy8nMb27A0U_w20wd4h-4fsMGHXuoLwHnTQRNy3dEjzKa0u7r07isiAJ3fYU1hB-kuk-Prg31LC0tu3QJufwt8jRPm4HNcHnKzs786MsMx-lVWw1xnIuHi-64SrRSdlJ4jDCX-UC8TkYmE3tQNA-dui76LjvUKp_6hPnVUsG8sMdFEkDwCYKNPfYZctqVGefOq6GZvWN8cocZybjsDcTnMMdJq9DIGaEgsaaT6SzKx8i1W1bWYRgojPzR-_U_D60Xm4Rz231IOGWd38-_udQLTcSCldJ7c6Am-gAJ7w4ujrAJ478j_xZiqQcg7BFNtw_0isYgY5klyVjQy_uz3giP0uOio4LFd8c8J3YpxwR6Z79H9DdKibnkdD5jitOCgwKHmbCQWqtSQA'
       ```
     Response:

       ```
         {
            "links": [
              {
                 "rel": "self",
                 "href": "http://localhost:8082/api/v1/student/all?page=0&size=2"
              }
             ],
           "content": [
              {
                 "id": "fbe6ef68-77e1-492e-832a-d3ce068ef2f0",
                 "user": {
                           "id": "80916b7f-8c8b-40af-abee-d544c50812e7",
                           "username": "jose.mora",
                           "firstName": "Jose",
                           "lastName": "Mora",
                           "email": "josealejonino@gmail.com",
                           "password": "Ciphered...",
                           "role": "ROLE_USER",
                           "links": [
                             {
                                "rel": "user-signin",
                                "href": "http://localhost:8082/api/v1/auth/token"
                             }
                            ]
                         },
                "courses": [],
                "links": [
                  {
                     "rel": "self",
                     "href": "http://localhost:8082/api/v1/student/fbe6ef68-77e1-492e-832a-d3ce068ef2f0/fbe6ef68-77e1-492e-832a-d3ce068ef2f0"
                  }
                 ]
              }
            ],
           "page": {
                     "size": 2,
                     "totalElements": 1,
                     "totalPages": 1,
                     "number": 0
                   }
         }
       ```
   * [Enroll Student in Course (PATCH)](http://localhost:8082/api/v1/student/enroll/{{studentId}}/{{courseId}}): *Enroll student in selected course.*

     Request:

       ```
        curl --location --request POST 'http://localhost:8082/api/v1/course/' \
             --header 'Content-Type: application/json' \
             --header 'Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJzY290dDIiLCJyb2xlcyI6WyJBRE1JTiJdLCJpc3MiOiJTY2hvb2wgUmVnaXN0cmF0aW9uIEFQSSIsImV4cCI6MTY0MzkwMzQ3OSwiaWF0IjoxNjQyOTAzNDc5fQ.ObwDWp2Y2tiXWS8yfJqM7ifyA1xvsyKXnlf-zIjmpVsubw1F8ZUm2cOu-Axi0OrcjkIhN6zib8EWvhEErR5Fhb1X6Uhz6NGrAvNDO_XtsoSfZC6O5QzGS0BFIAflaJr6SZDzajCU5mlP9GQulonDdisu9Qh18eP2fj8D4r0Wqy_a-pSZLqsLlxWy2id2IUv9_wphxUIxcyLltIQwzbjmMpY1H6Tjg6YxZOUBvWwfg9eR1oFwOqFcACpwZXM9uxK5KiV-pvCZVze6ISgJa_hFmlueCBsk_iPRKy8nMb27A0U_w20wd4h-4fsMGHXuoLwHnTQRNy3dEjzKa0u7r07isiAJ3fYU1hB-kuk-Prg31LC0tu3QJufwt8jRPm4HNcHnKzs786MsMx-lVWw1xnIuHi-64SrRSdlJ4jDCX-UC8TkYmE3tQNA-dui76LjvUKp_6hPnVUsG8sMdFEkDwCYKNPfYZctqVGefOq6GZvWN8cocZybjsDcTnMMdJq9DIGaEgsaaT6SzKx8i1W1bWYRgojPzR-_U_D60Xm4Rz231IOGWd38-_udQLTcSCldJ7c6Am-gAJ7w4ujrAJ478j_xZiqQcg7BFNtw_0isYgY5klyVjQy_uz3giP0uOio4LFd8c8J3YpxwR6Z79H9DdKibnkdD5jitOCgwKHmbCQWqtSQA' \
             --data-raw '{
                           "name": "math3"
             }'
       ```
     Response:

       ```
         {
           "id": "fbe6ef68-77e1-492e-832a-d3ce068ef2f0",
           "user": {
                     "id": "80916b7f-8c8b-40af-abee-d544c50812e7",
                     "username": "jose.mora",
                     "firstName": "Jose",
                     "lastName": "Mora",
                     "email": "josealejonino@gmail.com",
                     "password": "Ciphered...",
                     "role": "ROLE_USER",
                     "links": [
                       {
                          "rel": "user-signin",
                          "href": "http://localhost:8082/api/v1/auth/token"
                       }
                      ]
                   },
           "courses": [
              {
                "id": "ab0bdfa7-75a4-4d62-95b6-506a21186db4",
                "name": "math3",
                "students": null,
                "links": []
              }
             ],
           "links": [
             {
                "rel": "self",
                "href": "http://localhost:8082/api/v1/student/fbe6ef68-77e1-492e-832a-d3ce068ef2f0/fbe6ef68-77e1-492e-832a-d3ce068ef2f0"
             }
            ]
         }
       ```
   * [Unroll Student from Course (PATCH)](http://localhost:8082/api/v1/student/unroll/{{studentId}}/{{courseId}}): *Unroll student from selected course.*

     Request:

       ```
        curl --location --request PATCH 'http://localhost:8082/api/v1/student/unroll/fbe6ef68-77e1-492e-832a-d3ce068ef2f0/ab0bdfa7-75a4-4d62-95b6-506a21186db4' \
             --header 'Content-Type: application/json' \
             --header 'Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJzY290dDIiLCJyb2xlcyI6WyJBRE1JTiJdLCJpc3MiOiJTY2hvb2wgUmVnaXN0cmF0aW9uIEFQSSIsImV4cCI6MTY0MzkwMzQ3OSwiaWF0IjoxNjQyOTAzNDc5fQ.ObwDWp2Y2tiXWS8yfJqM7ifyA1xvsyKXnlf-zIjmpVsubw1F8ZUm2cOu-Axi0OrcjkIhN6zib8EWvhEErR5Fhb1X6Uhz6NGrAvNDO_XtsoSfZC6O5QzGS0BFIAflaJr6SZDzajCU5mlP9GQulonDdisu9Qh18eP2fj8D4r0Wqy_a-pSZLqsLlxWy2id2IUv9_wphxUIxcyLltIQwzbjmMpY1H6Tjg6YxZOUBvWwfg9eR1oFwOqFcACpwZXM9uxK5KiV-pvCZVze6ISgJa_hFmlueCBsk_iPRKy8nMb27A0U_w20wd4h-4fsMGHXuoLwHnTQRNy3dEjzKa0u7r07isiAJ3fYU1hB-kuk-Prg31LC0tu3QJufwt8jRPm4HNcHnKzs786MsMx-lVWw1xnIuHi-64SrRSdlJ4jDCX-UC8TkYmE3tQNA-dui76LjvUKp_6hPnVUsG8sMdFEkDwCYKNPfYZctqVGefOq6GZvWN8cocZybjsDcTnMMdJq9DIGaEgsaaT6SzKx8i1W1bWYRgojPzR-_U_D60Xm4Rz231IOGWd38-_udQLTcSCldJ7c6Am-gAJ7w4ujrAJ478j_xZiqQcg7BFNtw_0isYgY5klyVjQy_uz3giP0uOio4LFd8c8J3YpxwR6Z79H9DdKibnkdD5jitOCgwKHmbCQWqtSQA'
       ```
     Response:

       ```
         {
            "id": "fbe6ef68-77e1-492e-832a-d3ce068ef2f0",
            "user": {
                      "id": "80916b7f-8c8b-40af-abee-d544c50812e7",
                      "username": "jose.mora",
                      "firstName": "Jose",
                      "lastName": "Mora",
                      "email": "josealejonino@gmail.com",
                      "password": "Ciphered...",
                      "role": "ROLE_USER",
                      "links": [
                        {
                           "rel": "user-signin",
                           "href": "http://localhost:8082/api/v1/auth/token"
                        }
                       ]
                    },
            "courses": [],
            "links": [
              {
                 "rel": "self",
                 "href": "http://localhost:8082/api/v1/student/fbe6ef68-77e1-492e-832a-d3ce068ef2f0/fbe6ef68-77e1-492e-832a-d3ce068ef2f0"
              }
             ]
         }
       ```
   * [Get Student enrolled in Course (GET)/**ADMIN**](http://localhost:8082/api/v1/student/course/{{courseId}}): *Get all the students enrroled in the specified course.*

   Request:

     ```
      curl --location --request GET 'http://localhost:8082/api/v1/student/course/ab0bdfa7-75a4-4d62-95b6-506a21186db4' \
           --header 'Content-Type: application/json' \
           --header 'Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJzY290dDIiLCJyb2xlcyI6WyJBRE1JTiJdLCJpc3MiOiJTY2hvb2wgUmVnaXN0cmF0aW9uIEFQSSIsImV4cCI6MTY0MzkwMzQ3OSwiaWF0IjoxNjQyOTAzNDc5fQ.ObwDWp2Y2tiXWS8yfJqM7ifyA1xvsyKXnlf-zIjmpVsubw1F8ZUm2cOu-Axi0OrcjkIhN6zib8EWvhEErR5Fhb1X6Uhz6NGrAvNDO_XtsoSfZC6O5QzGS0BFIAflaJr6SZDzajCU5mlP9GQulonDdisu9Qh18eP2fj8D4r0Wqy_a-pSZLqsLlxWy2id2IUv9_wphxUIxcyLltIQwzbjmMpY1H6Tjg6YxZOUBvWwfg9eR1oFwOqFcACpwZXM9uxK5KiV-pvCZVze6ISgJa_hFmlueCBsk_iPRKy8nMb27A0U_w20wd4h-4fsMGHXuoLwHnTQRNy3dEjzKa0u7r07isiAJ3fYU1hB-kuk-Prg31LC0tu3QJufwt8jRPm4HNcHnKzs786MsMx-lVWw1xnIuHi-64SrRSdlJ4jDCX-UC8TkYmE3tQNA-dui76LjvUKp_6hPnVUsG8sMdFEkDwCYKNPfYZctqVGefOq6GZvWN8cocZybjsDcTnMMdJq9DIGaEgsaaT6SzKx8i1W1bWYRgojPzR-_U_D60Xm4Rz231IOGWd38-_udQLTcSCldJ7c6Am-gAJ7w4ujrAJ478j_xZiqQcg7BFNtw_0isYgY5klyVjQy_uz3giP0uOio4LFd8c8J3YpxwR6Z79H9DdKibnkdD5jitOCgwKHmbCQWqtSQA'
     ```
   Response:

     ```
       {
         "links": [
            {
              "rel": "self",
              "href": "http://localhost:8082/api/v1/student/course/ab0bdfa7-75a4-4d62-95b6-506a21186db4?page=0&size=20"
            }
           ],
         "content": [
            {
               "id": "fbe6ef68-77e1-492e-832a-d3ce068ef2f0",
               "user": {
                   "id": "80916b7f-8c8b-40af-abee-d544c50812e7",
                   "username": "jose.mora",
                   "firstName": "Jose",
                   "lastName": "Mora",
                   "email": "josealejonino@gmail.com",
                   "password": "Ciphered...",
                   "role": "ROLE_USER",
                   "links": [
                      {
                        "rel": "user-signin",
                        "href": "http://localhost:8082/api/v1/auth/token"
                      }
                     ]
                  },
               "courses": [
                  {
                     "id": "ab0bdfa7-75a4-4d62-95b6-506a21186db4",
                     "name": "math3",
                     "students": null,
                     "links": []
                  }
                 ],
               "links": [
                  {
                     "rel": "self",
                     "href": "http://localhost:8082/api/v1/student/fbe6ef68-77e1-492e-832a-d3ce068ef2f0/fbe6ef68-77e1-492e-832a-d3ce068ef2f0"
                  }
                 ]
            },
            {
              "id": "c9f042a8-8dda-45d1-836e-4ba1d3c2079d",
              "user": {
                  "id": "ec4e795d-56de-43ed-8631-6b9f0cbc38d3",
                  "username": "alejo.nino",
                  "firstName": "Alejo",
                  "lastName": "Nino",
                  "email": "alejo.nino@gmail.com",
                  "password": "Ciphered...",
                  "role": "ROLE_USER",
                  "links": [
                    {
                        "rel": "user-signin",
                        "href": "http://localhost:8082/api/v1/auth/token"
                    }
                   ]
            },
            "courses": [
                {
                    "id": "ab0bdfa7-75a4-4d62-95b6-506a21186db4",
                    "name": "math3",
                    "students": null,
                    "links": []
                }
              ],
            "links": [
                {
                    "rel": "self",
                    "href": "http://localhost:8082/api/v1/student/c9f042a8-8dda-45d1-836e-4ba1d3c2079d/c9f042a8-8dda-45d1-836e-4ba1d3c2079d"
                }
              ]
            }
          ],
         "page": {
            "size": 20,
            "totalElements": 2,
            "totalPages": 1,
            "number": 0
           }
       }
     ```
   * [Get Course without Students (GET)/**ADMIN**](http://localhost:8082/api/v1/student/no-courses): *Get all courses without students.*

   Request:

       ```
        curl --location --request GET 'http://localhost:8082/api/v1/student/no-courses' \
             --header 'Content-Type: application/json' \
             --header 'Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJzY290dDIiLCJyb2xlcyI6WyJBRE1JTiJdLCJpc3MiOiJTY2hvb2wgUmVnaXN0cmF0aW9uIEFQSSIsImV4cCI6MTY0MzkwMzQ3OSwiaWF0IjoxNjQyOTAzNDc5fQ.ObwDWp2Y2tiXWS8yfJqM7ifyA1xvsyKXnlf-zIjmpVsubw1F8ZUm2cOu-Axi0OrcjkIhN6zib8EWvhEErR5Fhb1X6Uhz6NGrAvNDO_XtsoSfZC6O5QzGS0BFIAflaJr6SZDzajCU5mlP9GQulonDdisu9Qh18eP2fj8D4r0Wqy_a-pSZLqsLlxWy2id2IUv9_wphxUIxcyLltIQwzbjmMpY1H6Tjg6YxZOUBvWwfg9eR1oFwOqFcACpwZXM9uxK5KiV-pvCZVze6ISgJa_hFmlueCBsk_iPRKy8nMb27A0U_w20wd4h-4fsMGHXuoLwHnTQRNy3dEjzKa0u7r07isiAJ3fYU1hB-kuk-Prg31LC0tu3QJufwt8jRPm4HNcHnKzs786MsMx-lVWw1xnIuHi-64SrRSdlJ4jDCX-UC8TkYmE3tQNA-dui76LjvUKp_6hPnVUsG8sMdFEkDwCYKNPfYZctqVGefOq6GZvWN8cocZybjsDcTnMMdJq9DIGaEgsaaT6SzKx8i1W1bWYRgojPzR-_U_D60Xm4Rz231IOGWd38-_udQLTcSCldJ7c6Am-gAJ7w4ujrAJ478j_xZiqQcg7BFNtw_0isYgY5klyVjQy_uz3giP0uOio4LFd8c8J3YpxwR6Z79H9DdKibnkdD5jitOCgwKHmbCQWqtSQA'
       ```
   Response:

       ```
         {
           "links": [
             {
                "rel": "self",
                "href": "http://localhost:8082/api/v1/student/no-courses?page=0&size=20"
             }
            ],
           "content": [
             {
                "id": "a8a765ae-a277-4ad2-b7a7-f93dae63f516",
                "user": {
                     "id": "3951c5cd-f4b8-468c-8844-91e420342718",
                     "username": "lindsay.guerrero",
                     "firstName": "Lindsay",
                     "lastName": "Guerrero",
                     "email": "lindsay.guerrero@gmail.com",
                     "password": "Ciphered...",
                     "role": "ROLE_USER",
                     "links": [
                       {
                          "rel": "user-signin",
                          "href": "http://localhost:8082/api/v1/auth/token"
                       }
                      ]
                 },
                "courses": [],
                "links": [
                  {
                     "rel": "self",
                     "href": "http://localhost:8082/api/v1/student/a8a765ae-a277-4ad2-b7a7-f93dae63f516/a8a765ae-a277-4ad2-b7a7-f93dae63f516"
                  }
                 ]
             }
            ],
           "page": {
                 "size": 20,
                 "totalElements": 1,
                 "totalPages": 1,
                 "number": 0
             }
         }
       ```                                    
### API features

* Java/Spring boot maven project.
* MySQL DB
* In memory DB for testing (H2)
* Spring Oauth2 Authentication/Authorization
* JWT token
* Hateoas support
* Etag support
* [Swagger documentation](http://localhost:8082/v2/api-docs) and [Swagger UI](http://localhost:8082/swagger-ui.html)
* [PostMan collection](postman/Minesweeper.postman_collection.json)
* [Docker Compose containers orchestration](docker-compose.yaml)

### How to run the API

* From the console line in the root folder of the project execute the following command to generate the .jar file.
  ```
  ./mvnw spring-boot:build-image
  ```
* Now run the application by:

   ```
   docker-compose up -d
  
   ```
* The API by default will be reachable in the following URL *http://localhost:8082*
* *(Optional)* Use [PostMan collection](postman/School-Registration.postman_collection.json) to hit the endpoints.



### Code Coverage
Complete coverage of the following layers:
* Controller
* Service
* Entities
* Exceptions
* Hateoas

![](images/coverage.png)

#### Web version

![](images/coverageReport.png)

### Swagger UI

![](images/swagger-UI.png)

1. Authentication
   ![](images/Swagger-Authentication.png)
2. Courses
   ![](images/Swagger-Courses.png)
3. Students
   ![](images/Swagger-Students.png)
