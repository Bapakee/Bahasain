const { UserProgress, Module, Level, Sequelize } = require('../models');

// Fungsi untuk mendapatkan modul dengan level dan status penyelesaian
const getModules = async (req, res) => {
  const userId = req.userId; // ID pengguna dari token

  try {
    // Ambil progress terakhir dari user berdasarkan user_id
    const userProgress = await UserProgress.findAll({
      where: { user_id: userId },
      include: [
        {
          model: Level,
          include: [Module], // Sertakan modul terkait dengan level
        },
      ],
      order: [['updated_at', 'DESC']], // Urutkan berdasarkan progress terbaru
    });

    // Jika tidak ada data progress
    if (!userProgress) {
      return res.status(404).json({
        status: 'failed',
        message: 'No progress data found for user',
        isSuccess: false,
        data: null,
      });
    }

    // Ambil semua modul dan level yang ada untuk user
    const modulesData = await Module.findAll({
      include: {
        model: Level,
        where: {
          level: {
            [Sequelize.Op.lte]: userProgress[0].level_id, // Level yang sudah atau sedang dipelajari
          },
        },
      },
    });

    const result = [];

    // Untuk setiap modul, ambil semua level dan status penyelesaian
    for (const module of modulesData) {
      const levels = module.Levels;
      const userLevelsStatus = [];

      for (const level of levels) {
        // Ambil status penyelesaian untuk user di setiap level
        const progress = await UserProgress.findOne({
          where: {
            user_id: userId,
            module_id: module.id,
            level_id: level.id,
          },
        });

        const status = progress && progress.completed ? 'completed' : 'in-progress';
        
        userLevelsStatus.push({
          levelId: level.id,
          levelTitle: level.title,
          status: status, // Status penyelesaian level
        });
      }

      result.push({
        moduleId: module.id,
        moduleName: module.name,
        levels: userLevelsStatus, // Daftar level dan status penyelesaian
      });
    }

    res.status(200).json({
      status: 'success',
      message: 'Modules and levels data fetched successfully',
      data: result,
    });
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

module.exports = { getModules };
