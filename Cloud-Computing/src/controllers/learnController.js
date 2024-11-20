const { UserProgress, Module, Level, Quiz, Content, QuizOption, Sequelize, User } = require('../models');
const { Op, where } = require('sequelize');
const quiz = require('../models/quiz');
const quizoption = require('../models/quizoption');

// Fungsi untuk mendapatkan modul dengan level dan status penyelesaian
const getModules = async (req, res) => {
    const userId = req.user.id // ID pengguna dari token

    try {
        const user = await User.findByPk(userId)
        if (!user) {
            return res.status(401).json({ error: 'user tidak ada' })
        }
        // Fetch all modules where the level is <= user's level
        const modules = await Module.findAll({
            where: {
                level: {
                    [Op.lte]: user.userLevel, // Level module must be <= user's level
                    [Op.gt]: 0, // Level module must be greater than 0
                },
            },
            order: [['level', 'ASC']], // Order by level in ascending order
            include: [
                {
                    model: Level, // Include related Levels for each Module
                    include: [
                        {
                            model: UserProgress, // Include UserProgress to check completion status
                            where: { userId },  // Only include progress for the specific user
                            required: false,  // It's a left join, so no need for UserProgress to exist
                            attributes: ['completed'],  // Only get 'completed' attribute from UserProgress
                        }
                    ]
                }
            ],
        });
        // Transform the data to match the desired response format
        const response = modules.map(module => ({
            id: module.id,
            name: module.name,
            level: module.level,
            levels: module.Levels.map(level => ({
                id: level.id,
                title: level.title,
                complete: level.UserProgresses?.length > 0 ? level.UserProgresses[0].completed : false  // Check if user has completed this level
            }))
        }));

        // Return the formatted response
        res.status(200).json({ module: response });
    } catch (error) {
        console.error('Error fetching modules and levels:', error);
        res.status(500).json({
            status: 'failed',
            message: 'Error fetching modules and levels',
            isSuccess: false,
            data: null,
        });
    }
};
const getLevel = async (req, res) => {
    const { moduleId, levelId } = req.params
    try {
        const levels = await Level.findAll({
            where : {
                moduleId : moduleId,
                id : levelId
            },
            include : [
                {
                    model : Content,

                },
                {
                    model: Quiz,
                    include: [
                        {
                            model: QuizOption,
                        }
                    ]
                }
            ]
        })
        if(!levels||levels.length<1){
            return res.status(401).json({ error: 'level tidak tersedia' })
        }
        const response = levels.map(level=>({
            id:level.id,
            title:level.title,
            contents:level.Contents.map(content=>({
                id:content.id,
                contentEn : content.contentEn,
                contentId : content.contentId,
                sequence : content.sequence,
                transliteration : content.transliteration,
                category : content.category
            })),
            quizzes : level.Quizzes.map(quiz=>({
                id : quiz.id,
                type : quiz.type,
                question : quiz.question,
                answer : quiz.answer,
                quizOption : quiz.QuizOptions.map(quizOption=>({
                    id:quizOption.id,
                    option : quizOption.option
                }))
            }))
        }))
        res.status(200).json(response)
    }catch(error){
        console.error('Error fetching level:', error);
        res.status(500).json({
            status: 'failed',
            message: 'Error fetching level content',
            isSuccess: false,
            data: null,
        });
    }
}

module.exports = { getModules, getLevel };
