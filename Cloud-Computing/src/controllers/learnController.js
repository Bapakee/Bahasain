const { UserProgress, Module, Level, Quiz, QuizOption, Sequelize, User } = require('../models');
const { Op, where } = require('sequelize');

// Fungsi untuk mendapatkan modul dengan level dan status penyelesaian
const getModules = async (req, res) => {
    const userId = req.user.id; // ID pengguna dari token

    try {
        const user = await User.findByPk(userId);
        if (!user) {
            return res.status(401).json({ error: 'User tidak ada' });
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
                            where: { userId }, // Only include progress for the specific user
                            required: false, // It's a left join, so no need for UserProgress to exist
                            attributes: ['id', 'completed', 'score'], // Get 'id', 'completed', and 'score' attributes
                        },
                    ],
                },
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
                order : level.order,
                score: level.UserProgresses?.length > 0
                    ? level.UserProgresses[0].score
                    : null, // Include progress ID and score if exists
            })),
        }));

        // Return the formatted response
        res.status(200).json(response);
    } catch (error) {
        console.error('Error fetching modules and levels:', error);
        res.status(500).json({
            status: 'failed',
            message: 'Error fetching modules and levels',
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
            order:[['order','ASC']],
            include : [
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
        });
    }
}



module.exports = { getModules, getLevel };
