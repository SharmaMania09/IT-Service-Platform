pipeline
{
    agent any

    environment
    {
        SPRING_DATASOURCE_URL = 'jdbc:mysql://mysql:3306/switch_platform'
        SPRING_DATASOURCE_USERNAME = 'root'
        SPRING_DATASOURCE_PASSWORD = 'root'
        SPRING_DATA_REDIS_HOST = 'redis'
        SPRING_DATA_REDIS_PORT = '6379'
    }
    
    stages
    {
        stage('Environment Check')
        {
            steps
            {
                sh 'java -version'
                sh 'git --version'
                sh './mvnw --version'
            }
        }
        
        stage('Build')
        {
            steps
            {
                sh './mvnw clean compile'
            }
        }

        stage('Test')
        {
            steps
            {
                sh './mvnw test'
            }
        }

        stage('Package')
        {
            steps
            {
                sh './mvnw package -DskipTests'
            }
        }

        stage('Checking Package Contents')
        {
            steps
            {
                sh 'ls -lh target/'
            }
        }

        stage('Docker Build')
        {
            steps
            {
                sh 'docker build -t switch_platform .'
            }
        }
    }
}