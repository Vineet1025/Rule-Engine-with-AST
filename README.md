<div align="center">
  <h1>Rule Engine with Abstract Syntax Tree (AST)</h1>
  <h3>
  :zap: <a href="http://13.201.204.129:9000/api/rule/index">Live Project </a>
</h3>
</div>



<!-- Table of Contents -->
# :notebook_with_decorative_cover: Table of Contents

- [About the Project](#star2-about-the-project)
  * [Screenshots](#camera-screenshots)
  * [Technologies Used](#space_invader-technologies-used)
  * [Features](#dart-features)
  * [Environment Variables](#key-environment-variables)
- [Getting Started](#toolbox-getting-started)
  * [Prerequisites](#bangbang-prerequisites)
  * [Installation](#gear-installation)
- [Deployment](#deployment)
- [Usage](#eyes-usage)
- [Contributing](#wave-contributing)
- [License](#warning-license)
- [Contact](#handshake-contact)
- [Acknowledgements](#gem-acknowledgements)

<!-- About the Project -->
## :star2: About the Project
The Rule Engine with Abstract Syntax Tree (AST) is a dynamic application designed to create, evaluate, and combine business rules based on user-defined criteria. This engine uses an AST to represent the logical structure of rules and allows for complex rule evaluation efficiently. The project is built using Spring Boot, Hibernate, and Thymeleaf for an intuitive user interface and seamless backend operations.
<!-- Screenshots -->
### :camera: Screenshots

<div align="center">
  <table>
    <tr>
      <td><img src="https://res.cloudinary.com/divq45mjo/image/upload/v1729698217/Screenshot_2024-10-23_211007_ypi60p.png" alt="screenshot" width="400"/></td>
      <td><img src="https://res.cloudinary.com/divq45mjo/image/upload/v1729698217/Screenshot_2024-10-23_211059_ck3ps6.png" width="400"/></td>
    </tr>
  </table>
</div>

<!-- TechStack -->
### :space_invader: Technologies Used

<details>
  <summary>Backend</summary>
  <ul>
    <li><a href="https://www.java.com/en/">Java</a></li>
    <li><a href="https://spring.io/projects/spring-boot">Spring Boot</a></li>
    <li><a href="https://hibernate.org/">Hibernate</a></li>
  </ul>
</details>

<details>
  <summary>Frontend</summary>
  <ul>
    <li><a href="https://www.thymeleaf.org/">Thymeleaf</a></li>
    <li><a href="https://html.com/">HTML</a></li>
    <li><a href="https://www.w3.org/Style/CSS/Overview.en.html">CSS</a></li>
    <li><a href="https://www.javascript.com/">JavaScript</a></li>
  </ul>
</details>

<details>
<summary>Database</summary>
  <ul>
    <li><a href="https://www.mysql.com/">MySQL</a></li>
  </ul>
</details>

<details>
<summary>Deployment</summary>
  <ul>
    <li><a href="https://aws.amazon.com/ec2/">AWS EC2</a></li>
  </ul>
</details>

<!-- Features -->
### :dart: Features

- **Dynamic Rule Evaluation**: Evaluate user eligibility based on dynamically defined rules.
  
- **Rule Combination**: Combine existing rules into a single AST with OR/AND operations.
  
- **Error Handling**: Inform users if a rule is already combined with another.
  
- **User-Friendly Interface**: Easy navigation and interaction for rule management.

<!-- Env Variables -->
### :key: Environment Variables

To run this project, you will need to add the following environment variables to your .env file or application.properties:

- `db_url`
- `db_username`
- `db_password`

<!-- Getting Started -->
## :toolbox: Getting Started

<!-- Prerequisites -->
### :bangbang: Prerequisites

- Java 17
- Spring Boot 3.2.5
- MySQL
- Maven

<!-- Installation -->
### :gear: Installation

1. **Clone the repository**:

   ```bash
   git clone https://github.com/Vineet1025/Rule-Engine-with-AST.git
   cd Rule-Engine-with-AST
   ```
2. **Configure your application.properties for database connection**:
   
   ```bash
   spring.datasource.url=${db_url} //Enter your database URL (jdbc:mysql://localhost:3306/rule_engine_db)
   spring.datasource.username=${db_username} //Enter your database username
   spring.datasource.password=${db_password} //Enter your database password
   ```
   
3. **Run the application**:

   ```bash
   mvn spring-boot:run
    ```
   
4. **Access the application in your browser at** http://localhost:8080/api/rule/index

### :triangular_flag_on_post: Deployment

The application has been deployed on **AWS EC2**, allowing for easy access and management of rule evaluation.

**Accessing the Deployed Application**
- You can access the live application at: http://13.201.204.129:9000/api/rule/index

<!-- Usage -->
## :eyes: Usage
1. **Create Rules**: Users can navigate to the "Create Rule" section to define new rules based on user attributes.

2. **Combine Rules**: Select multiple existing rules from checkboxes to combine them, enhancing the logic of evaluations.

3. **Evaluate Rules**: Test the defined rules against user data to check for eligibility or compliance.

<!-- Contributing -->
## :wave: Contributing
  <img src="https://contrib.rocks/image?repo=Louis3797/awesome-readme-template" />

Contributions are welcome! Please feel free to submit issues or pull requests.

<!-- License -->
## :warning: License

This project is licensed under the MIT License - see the [LICENSE](https://github.com/Vineet1025/Rule-Engine-with-AST/blob/master/LICENSE.md) file for details.

<!-- Contact -->
## :handshake: Contact

Vineet Jain - [LinkedIn](https://www.linkedin.com/in/vineet-jain1025/) - jvineet1025@gmail.com

Project Link: [https://github.com/Vineet1025/Weather-App](https://github.com/Vineet1025/Weather-App)

<!-- Acknowledgments -->
## :gem: Acknowledgements

- Spring Boot for providing an efficient backend framework.
- MySQL for data management.
