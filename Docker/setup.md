docker run -d `
  --name jenkins-master `
  --network jenkins `
  -p 8080:8080 `
  -p 50000:50000 `
  -v jenkins_home:/var/jenkins_home `
  jenkins/jenkins:lts

  docker run -d `
  --name jenkins-agent-1 `
  --network jenkins `
  jenkins/inbound-agent `
  -url http://jenkins-master:8080 `
  -secret <SECRET_FOR_AGENT_1> `
  -name agent-1 `
  -workDir /home/jenkins/agent

docker run -d `
  --name jenkins-agent-2 `
  --network jenkins `
  jenkins/inbound-agent `
  -url http://jenkins-master:8080 `
  -secret <SECRET_FOR_AGENT_2> `
  -name agent-2 `
  -workDir /home/jenkins/agent