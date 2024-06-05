evaluation {
    taskScore = 1.0
    firstDeadLinePenalty = -0.5
    secondDeadLinePenalty = -0.5
    jacocoPercentage = 80
    jacocoScore = 1.0
    checkStyleScore = 1.0
}

git {
    repoLinkPrefix = 'https://github.com/' // possible to switch to ssh
    repoLinkPostfix = '.git'
    defaultRepository = 'oop'
    docsBranch = 'gh-pages'
    defaultBranch = 'main'
}

enabledAchievements = [Achievement.EXCELLENT_PERFORMANCE,
                       Achievement.PERFECT_ATTENDANCE]