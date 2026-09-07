pipleine
{
    agent any

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
    }
}